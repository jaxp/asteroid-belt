package com.pallas.service.user.config;

import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.service.IPlsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/8/21 16:47
 * @desc:
 */
public class AbstractWebSecurityConfig {

    @Autowired
    private IPlsUserService plsUserService;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = username -> {
            PlsUser user = plsUserService.query()
                .eq("username", username)
                .one();
            if (Objects.isNull(user)) {
                throw new PlsException(ResultType.AUTHENTICATION_ERR, "用户名或密码错误");
            }
            return user;
        };
        return userDetailsService;
    }

}
