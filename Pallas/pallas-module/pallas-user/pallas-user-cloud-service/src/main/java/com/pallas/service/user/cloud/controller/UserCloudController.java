package com.pallas.service.user.cloud.controller;

import com.pallas.service.user.api.IPlsUserApi;
import com.pallas.service.user.dto.PlsUserDTO;
import com.pallas.service.user.service.IPlsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jax
 * @time: 2020/11/26 16:01
 * @desc:
 */
@RestController
@RequestMapping("/api/cloud/user")
public class UserCloudController implements IPlsUserApi {

    @Autowired
    private IPlsUserService plsUserService;

    @Override
    @GetMapping("/getCurrent")
    public PlsUserDTO getCurrent() {
        return plsUserService.getCurrent();
    }

    @Override
    @GetMapping("/getUser/{username}")
    public PlsUserDTO getUser(@PathVariable String username) {
        return plsUserService.getUser(username);
    }

}
