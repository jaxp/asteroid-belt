package com.pallas.service.user.service.impl;

import com.pallas.service.user.bean.User;
import com.pallas.service.user.dto.UserDTO;
import com.pallas.service.user.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author: jax
 * @time: 2020/8/13 16:53
 * @desc:
 */
@Service
public class UserService implements IUserService {
  @Override
  public UserDTO getUser() {
    User user = new User()
        .setId(123L)
        .setUsername("admin")
        .setPassword("pwd");
    UserDTO userDTO = new UserDTO()
        .setId(user.getId())
        .setUsername(user.getUsername());
    return userDTO;
  }
}
