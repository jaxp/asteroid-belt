package com.pallas.server.cloud.user.interceptor;

import com.pallas.service.user.cache.TokenCache;
import com.pallas.service.user.cache.UserCache;
import com.pallas.service.user.constant.UserConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    @Autowired
    private UserCache userCache;
    @Autowired
    private TokenCache tokenCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader(UserConstant.USER_ID_HEADER);
        if (StringUtils.isNotBlank(userId)) {
            userCache.setUserId(Long.parseLong(userId));
        }
        String token = request.getHeader(UserConstant.TOKEN_HEADER);
        if (StringUtils.isNotBlank(token)) {
            tokenCache.setContext(token);
        }
        return true;
    }

}
