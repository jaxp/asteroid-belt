package com.pallas.service.captcha.api;

/**
 * @author: jax
 * @time: 2020/11/29 10:19
 * @desc:
 */
public interface IPlsCaptchaApi {

    /**
     * 校验
     *
     * @param certificate
     * @return
     */
    boolean validate(String certificate);
}
