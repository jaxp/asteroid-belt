package com.pallas.server.cloud.user.controller;

import com.pallas.service.user.api.IPlsUserApi;
import com.pallas.service.user.dto.PlsUserDTO;
import com.pallas.service.user.service.IPlsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jax
 * @time: 2020/8/14 8:43
 * @desc:
 */
@RestController
@RequestMapping("/api/user/")
public class UserController implements IPlsUserApi {

    @Autowired
    private IPlsUserService plsUserService;

    @Override
    @GetMapping("/getCurrent")
    public PlsUserDTO getCurrent(@RequestParam Long userId) {
        return plsUserService.getCurrent(userId);
    }

    @Override
    @GetMapping("/getUser/{username}")
    public PlsUserDTO getUser(@PathVariable String username) {
        return plsUserService.getUser(username);
    }

}
