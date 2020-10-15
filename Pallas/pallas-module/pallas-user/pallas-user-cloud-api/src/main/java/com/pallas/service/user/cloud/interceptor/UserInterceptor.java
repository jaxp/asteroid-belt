package com.pallas.service.user.cloud.interceptor;

import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.cloud.context.UserContext;
import com.pallas.service.user.constant.UserConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/9/28 8:18
 * @desc:
 */
@Component
public class UserInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId = UserContext.getUserId();
        if (Objects.isNull(userId)) {
            throw new PlsException(ResultType.AUTHORIZATION_EXCEPTION);
        }
        requestTemplate.header(UserConstant.USER_ID_HEADER, userId + "");
    }
}
