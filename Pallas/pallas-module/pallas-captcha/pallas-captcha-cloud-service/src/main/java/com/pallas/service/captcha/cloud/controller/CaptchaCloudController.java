package com.pallas.service.captcha.cloud.controller;

import com.pallas.service.captcha.api.IPlsCaptchaApi;
import com.pallas.service.captcha.service.PlsCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jax
 * @time: 2020/11/26 16:01
 * @desc:
 */
@RestController
@RequestMapping("/api/cloud/captcha")
public class CaptchaCloudController implements IPlsCaptchaApi {

    @Autowired
    private PlsCaptchaService plsCaptchaService;

    @Override
    @GetMapping("/validate")
    public boolean validate(String certificate) {
        return plsCaptchaService.validate(certificate);
    }
}
