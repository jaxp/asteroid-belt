package com.pallas.service.user.config;

import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.bean.PlsUserAuthority;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsUserAuthorityService;
import com.pallas.service.user.service.IPlsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/8/21 16:47
 * @desc:
 */
public class AbstractWebSecurityConfig {

    @Autowired
    private IPlsUserService plsUserService;
    @Autowired
    private IPlsAuthorityService plsAuthorityService;
    @Autowired
    private IPlsUserAuthorityService plsUserAuthorityService;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = username -> {
            PlsUser user = plsUserService.query()
                .eq("username", username)
                .one();
            if (Objects.isNull(user)) {
                throw new PlsException(ResultType.AUTHENTICATION_ERR, "用户名或密码错误");
            }
            List<PlsUserAuthority> userAuthorities = plsUserAuthorityService.query()
                .eq("user_id", user.getId())
                .list();
            if (!userAuthorities.isEmpty()) {
                Set<Long> authIds = userAuthorities.stream()
                    .map(PlsUserAuthority::getAuthorityId)
                    .collect(Collectors.toSet());
                List<PlsAuthority> authorities = plsAuthorityService.listByIds(authIds);
                user.setAuthorities(authorities);
            }
            return user;
        };
        return userDetailsService;
    }

}
