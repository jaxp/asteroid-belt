package com.pallas.service.captcha.service;

import com.pallas.service.captcha.api.IPlsCaptchaApi;
import com.pallas.service.captcha.handler.ICaptchaHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: jax
 * @time: 2020/11/26 16:01
 * @desc:
 */
@Service
public class PlsCaptchaService implements IPlsCaptchaApi {

    @Autowired
    private ICaptchaHandler captchaHandler;

    @Override
    public boolean validate(String certificate) {
        return captchaHandler.existCertificate(certificate);
    }
}
