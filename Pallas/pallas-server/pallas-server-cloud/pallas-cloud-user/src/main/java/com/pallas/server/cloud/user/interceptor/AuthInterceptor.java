package com.pallas.server.cloud.user.interceptor;

import com.pallas.service.user.cache.TokenCacher;
import com.pallas.service.user.cache.UserCacher;
import com.pallas.service.user.constant.UserConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
    private UserCacher userCacher;
    @Autowired
    private TokenCacher tokenCacher;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader(UserConstant.USER_ID_HEADER);
        if (StringUtils.isNotBlank(userId)) {
            userCacher.setUserId(Long.parseLong(userId));
        }
        String token = request.getHeader(UserConstant.TOKEN_HEADER);
        if (StringUtils.isNotBlank(token)) {
            tokenCacher.setContext(Long.parseLong(token));
        }
        return true;
    }

}
