package com.pallas.service.user.controller;

import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.converter.PlsUserConverter;
import com.pallas.service.user.dto.PlsUserDTO;
import com.pallas.service.user.param.UserPwdLoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jax
 * @time: 2020/8/25 15:40
 * @desc:
 */
@RestController
@RequestMapping("/api/user")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private PlsUserConverter userConverter;

  @PostMapping("/login")
  public PlsUserDTO login(@RequestBody UserPwdLoginParam param) {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(param.getUsername(), param.getPassword());
    Authentication authentication = authenticationManager.authenticate(token);
    PlsUser user = (PlsUser) authentication.getPrincipal();
    return userConverter.do2dto(user);
  }

}
