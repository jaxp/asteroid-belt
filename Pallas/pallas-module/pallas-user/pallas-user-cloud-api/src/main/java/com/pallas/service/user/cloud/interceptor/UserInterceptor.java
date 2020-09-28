package com.pallas.service.user.cloud.interceptor;

import com.pallas.service.user.cloud.api.PlsUserClient;
import com.pallas.service.user.constant.UserConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: jax
 * @time: 2020/9/28 8:18
 * @desc:
 */
@Component
public class UserInterceptor implements RequestInterceptor {

    @Autowired
    PlsUserClient userClient;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(UserConstant.USER_ID_HEADER, userClient.getUserId() + "");
    }
}
