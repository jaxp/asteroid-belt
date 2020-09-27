package com.pallas.server.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.PlsResult;
import com.pallas.base.api.response.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/8/25 8:47
 * @desc:
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String handle(Exception e) throws JsonProcessingException {
        PlsResult result = null;
        if (e instanceof PlsException) {
            Throwable cause = Objects.isNull(e.getCause()) ? e : e.getCause();
            StackTraceElement stackTraceElement = cause.getStackTrace()[0];
            log.error("{}.{}[{}]:{}", stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getLineNumber(), e.getMessage(),
                Objects.nonNull(e.getCause()) ? e.getCause() : null);
            result = PlsResult.error(((PlsException) e).getCode(), e.getLocalizedMessage());
        } else {
            log.error("异常:{}", e.getMessage(), e);
            result = PlsResult.error(ResultType.GENERAL_ERR);
        }
        return new StringBuilder(PlsConstant.ERR_PREFIX).append(objectMapper.writeValueAsString(result)).toString();
    }

}
