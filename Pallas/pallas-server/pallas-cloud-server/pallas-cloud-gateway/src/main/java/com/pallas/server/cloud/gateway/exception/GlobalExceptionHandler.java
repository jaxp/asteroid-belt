package com.pallas.server.cloud.gateway.exception;

import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jax
 * @time: 2020/9/27 14:16
 * @desc:
 */
@Slf4j
public class GlobalExceptionHandler extends DefaultErrorWebExceptionHandler {

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = super.getError(request);
        log.error(error.getMessage(), error);
        Map<String, Object> result = new HashMap<>(2);
        if (error instanceof PlsException) {
            result.put("code", ((PlsException) error).getCode());
        } else if (error instanceof ResponseStatusException) {
            result.put("code", ((ResponseStatusException) error).getStatus().value());
        } else {
            result.put("code", ResultType.GENERAL_ERR.getCode());
        }
        result.put("msg", error.getMessage());
        return result;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        return 200;
    }
}
