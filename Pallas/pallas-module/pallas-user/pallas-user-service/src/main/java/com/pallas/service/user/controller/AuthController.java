package com.pallas.service.user.controller;

import com.pallas.base.api.constant.PlsConstant;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.converter.PlsUserConverter;
import com.pallas.service.user.dto.PlsUserDTO;
import com.pallas.service.user.param.UserPwdLoginParam;
import com.pallas.service.user.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: jax
 * @time: 2020/8/25 15:40
 * @desc:
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PlsUserConverter userConverter;
    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public PlsUserDTO login(@RequestBody UserPwdLoginParam param, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(param.getUsername(), param.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        PlsUser user = (PlsUser) authentication.getPrincipal();
        String token = authService.login(user);
        response.setHeader(PlsConstant.AUTHORIZATION_HEADER, token);
        return userConverter.do2dto(user);
    }

}
