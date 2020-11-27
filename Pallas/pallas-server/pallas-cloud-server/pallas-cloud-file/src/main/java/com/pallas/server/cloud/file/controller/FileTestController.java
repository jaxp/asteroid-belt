package com.pallas.server.cloud.file.controller;

import com.pallas.base.api.response.PlsResult;
import com.pallas.service.user.cloud.api.PlsUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jax
 * @time: 2020/11/26 17:53
 * @desc:
 */
@RestController
@RequestMapping("/api/file")
public class FileTestController {

    @Autowired
    private PlsUserClient plsUserClient;

    @GetMapping("/test")
    public PlsResult test() {
        return PlsResult.success(plsUserClient.getCurrent());
    }

}
