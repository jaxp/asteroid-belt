package com.pallas.service.user.service.impl;

import com.pallas.service.user.bean.User;
import com.pallas.service.user.dto.UserDTO;
import com.pallas.service.user.service.IUserService;

/**
 * @author: jax
 * @time: 2020/8/13 16:53
 * @desc:
 */
public class UserService implements IUserService {
  @Override
  public UserDTO getUser() {
    User user = new User()
        .id(123L)
        .username("admin")
        .password("pwd");
    UserDTO userDTO = new UserDTO()
        .id(user.id())
        .username(user.username());
    return userDTO;
  }
}
