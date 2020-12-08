package com.pallas.service.captcha.aspect;

import com.google.common.base.Strings;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.captcha.api.IPlsCaptchaApi;
import com.pallas.service.captcha.constant.CaptchaConstant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: jax
 * @time: 2020/12/6 15:56
 * @desc:
 */
@Aspect
@Component
public class CaptchaAspect {

    @Autowired
    private IPlsCaptchaApi plsCaptchaService;

    @Pointcut("@annotation(com.pallas.service.captcha.annotation.Captcha)")
    public void captchaAspect() {
    }

    @Around("captchaAspect()")
    public Object captcha(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String captcha = Strings.nullToEmpty(request.getHeader(CaptchaConstant.CAPTCHA_HEADER)).trim();
        if (!Strings.isNullOrEmpty(captcha) && plsCaptchaService.validate(captcha)) {
            // 参数信息获取
            Object[] args = joinPoint.getArgs();
            return joinPoint.proceed(args);
        }
        throw new PlsException(ResultType.CAPTCHA_INVALID);
    }
}
