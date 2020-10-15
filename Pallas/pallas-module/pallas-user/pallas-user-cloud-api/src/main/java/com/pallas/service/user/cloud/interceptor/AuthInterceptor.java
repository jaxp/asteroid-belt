package com.pallas.service.user.cloud.interceptor;

import com.pallas.service.user.cloud.context.UserContext;
import com.pallas.service.user.constant.UserConstant;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: jax
 * @time: 2020/10/15 13:37
 * @desc:
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader(UserConstant.USER_ID_HEADER);
        if (!StringUtils.isEmpty(userId)) {
            UserContext.setUserId(Long.parseLong(userId));
        }
        String token = request.getHeader(UserConstant.TOKEN_HEADER);
        if (!StringUtils.isEmpty(token)) {
            UserContext.setTokenId(Long.parseLong(token));
        }
        return true;
    }

}
