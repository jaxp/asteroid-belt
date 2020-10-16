package com.pallas.service.user.service.impl;

import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.cache.TokenCacher;
import com.pallas.service.user.cache.UserInfoCacher;
import com.pallas.service.user.dto.PlsUserDTO;
import com.pallas.service.user.properties.AuthProperties;
import com.pallas.service.user.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: jax
 * @time: 2020/9/4 9:47
 * @desc: 会话相关
 */
@Slf4j
@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserInfoCacher userInfoCacher;
    @Autowired
    private TokenCacher tokenCacher;
    @Autowired
    private AuthProperties authProperties;

    @Override
    public String login(PlsUser user) {
        return userInfoCacher.cacheUser(user, authProperties.getExpireTime());
    }

    @Override
    public void logout() {
        tokenCacher.clear();
        if (CollectionUtils.isEmpty(tokenCacher.tokenKeysOfSameUser())) {
            userInfoCacher.clear();
        }
    }

    @Override
    public PlsUserDTO getUser() {
        return userInfoCacher.getUser();
    }

    @Override
    public Boolean validate(String token) {
        return userInfoCacher.validate(token);
    }
}
