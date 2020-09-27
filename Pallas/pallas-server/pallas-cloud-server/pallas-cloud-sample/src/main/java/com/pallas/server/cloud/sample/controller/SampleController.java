package com.pallas.server.cloud.sample.controller;

import com.pallas.service.user.cloud.api.PlsUserClient;
import com.pallas.service.user.dto.PlsUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jax
 * @time: 2020/9/27 13:51
 * @desc:
 */
@RestController
@RequestMapping("/api/sample")
public class SampleController {

    @Autowired
    PlsUserClient plsUserClient;

    @GetMapping("/sampleUser")
    public PlsUserDTO sampleUser() {
        return plsUserClient.getCurrent(plsUserClient.getUserId());
    }
}
