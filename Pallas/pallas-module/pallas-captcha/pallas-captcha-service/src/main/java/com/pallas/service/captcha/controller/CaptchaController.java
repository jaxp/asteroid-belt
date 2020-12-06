package com.pallas.service.captcha.controller;

import com.google.common.base.Strings;
import com.pallas.base.api.response.PlsResult;
import com.pallas.service.captcha.builder.ICaptcha;
import com.pallas.service.captcha.builder.SlidingCaptcha;
import com.pallas.service.captcha.entity.CaptchaClue;
import com.pallas.service.captcha.handler.ICaptchaGear;
import com.pallas.service.captcha.handler.ICaptchaHandler;
import com.pallas.service.captcha.param.CaptchaParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    private ICaptchaGear captchaGear;

    @GetMapping("/slide")
    public PlsResult slide() {
        ICaptcha captcha = captchaHandler.build(SlidingCaptcha.class);
        CaptchaClue clue = captcha.build();
        return PlsResult.success(clue);
    }

    @PostMapping("/validate")
    public PlsResult validate(@RequestBody CaptchaParams params) {
        String cid = params.getCid();
        boolean enable = true;
        if (!captchaGear.enable() || Strings.isNullOrEmpty(cid) && !captchaGear.enable(cid)) {
            enable = false;
        }
        ICaptcha captcha = captchaHandler.build(SlidingCaptcha.class);
        String certificate = captcha.check(cid, params.getCode(), enable);
        return PlsResult.success(certificate);
    }

}
