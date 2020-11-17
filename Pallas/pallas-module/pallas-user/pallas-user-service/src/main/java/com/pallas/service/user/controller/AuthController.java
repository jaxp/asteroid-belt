package com.pallas.service.user.controller;

import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.PlsResult;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.cache.RsaKeyCacher;
import com.pallas.service.user.converter.PlsMenuConverter;
import com.pallas.service.user.converter.PlsUserConverter;
import com.pallas.service.user.param.UserPwdLoginParam;
import com.pallas.service.user.service.IAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/user/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IAuthService authService;
    @Autowired
    private PlsUserConverter userConverter;
    @Autowired
    private PlsMenuConverter menuConverter;
    @Autowired
    private RsaKeyCacher rsaKeyCacher;

    @GetMapping("/getKey")
    public PlsResult getKey() {
        return PlsResult.success(rsaKeyCacher.getPublicKeyStr());
    }

    @PostMapping("/login")
    public PlsResult login(@RequestBody UserPwdLoginParam param, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(param.getUsername(), rsaKeyCacher.decrypt(param.getPassword()));
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            PlsUser user = (PlsUser) authentication.getPrincipal();
            String token = authService.login(user);
            response.setHeader(PlsConstant.AUTHORIZATION_HEADER, token);
            return PlsResult.success(userConverter.do2dto(user));
        } catch (AuthenticationException e) {
            throw new PlsException(ResultType.AUTHENTICATION_ERR, e.getMessage(), e);
        }
    }

    @GetMapping("/getUser")
    public PlsResult getUser() {
        return PlsResult.success(userConverter.bo2vo(authService.getUser()));
    }

    @GetMapping("/getMenus")
    public PlsResult getMenus() {
        return PlsResult.success(menuConverter.bo2vo(authService.getMenus()));
    }

    @GetMapping("/getAuthorities")
    public PlsResult getAuthorities() {
        return PlsResult.success(authService.getAuthorities());
    }

    @GetMapping("/logout")
    public PlsResult logout() {
        authService.logout();
        return PlsResult.success();
    }

}
