package com.pallas.server.common.exception;

import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.PlsResult;
import com.pallas.base.api.response.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/8/25 8:47
 * @desc:
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String CLOUD_URI_PREFIX = "/api/cloud";

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handle(Exception e) {
        String uri = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest().getRequestURI();
        boolean isCloud = uri.contains(CLOUD_URI_PREFIX);

        PlsException exception = null;
        if (e instanceof PlsException) {
            Throwable cause = Objects.isNull(e.getCause()) ? e : e.getCause();
            StackTraceElement stackTraceElement = cause.getStackTrace()[0];
            log.error("{}.{}[{}]:{}", stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getLineNumber(), e.getMessage(),
                Objects.nonNull(e.getCause()) ? e.getCause() : null);
            exception = (PlsException) e;
        } else {
            log.error("异常:{}", e.getMessage(), e);
            exception = new PlsException(ResultType.GENERAL_ERR);
        }
        if (isCloud) {
            return new PlsException(exception.getCode(),
                new StringBuilder("【").append(uri).append("】").append(exception.getMessage()).toString());
        } else {
            return PlsResult.error(exception.getCode(), exception.getLocalizedMessage());
        }
    }

}
