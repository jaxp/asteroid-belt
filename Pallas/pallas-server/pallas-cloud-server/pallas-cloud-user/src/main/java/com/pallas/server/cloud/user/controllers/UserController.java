package com.pallas.server.cloud.user.controllers;

import com.pallas.service.user.api.IUserApi;
import com.pallas.service.user.dto.UserDTO;
import com.pallas.service.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jax
 * @time: 2020/8/14 8:43
 * @desc:
 */
@RestController
public class UserController implements IUserApi {

  @Autowired
  private IUserService userService;

  @Override
  @RequestMapping("/user")
  public UserDTO getUser() {
    System.out.println("--------------------");
    return userService.getUser();
  }

}
