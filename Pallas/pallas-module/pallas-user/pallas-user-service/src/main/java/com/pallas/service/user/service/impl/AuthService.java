package com.pallas.service.user.service.impl;

import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.cache.UserInfoCacher;
import com.pallas.service.user.properties.AuthProperties;
import com.pallas.service.user.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: jax
 * @time: 2020/9/4 9:47
 * @desc: 会话相关
 */
@Service
public class AuthService implements IAuthService {

  @Autowired
  private UserInfoCacher userInfoCacher;
  @Autowired
  private AuthProperties authProperties;

  @Override
  public String login(PlsUser user) {
    String token = userInfoCacher.cacheUser(user, authProperties.getExpireTime());
    return token;
  }

  @Override
  public boolean validate(String token) {
    return userInfoCacher.validate(token);
  }
}
