package com.pallas.service.captcha.controller;

import com.pallas.base.api.response.PlsResult;
import com.pallas.service.captcha.builder.ICaptcha;
import com.pallas.service.captcha.builder.SlidingCaptcha;
import com.pallas.service.captcha.entity.CaptchaClue;
import com.pallas.service.captcha.handler.ICaptchaHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jax
 * @time: 2020/11/28 15:30
 * @desc:
 */
@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    @Autowired
    private ICaptchaHandler captchaHandler;

    @GetMapping("/slide")
    public PlsResult slide() {
        ICaptcha captcha = captchaHandler.build(SlidingCaptcha.class);
        CaptchaClue clue = captcha.build();
        return PlsResult.success(clue);
    }

}
