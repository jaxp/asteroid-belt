package com.pallas.service.captcha.builder;

import com.pallas.service.captcha.entity.CaptchaResult;
import com.pallas.service.captcha.handler.ICaptchaGear;
import com.pallas.service.captcha.handler.ICaptchaHandler;

/**
 * @author: jax
 * @time: 2020/11/25 21:36
 * @desc:
 */
public abstract class AbstractCaptcha implements ICaptcha {

    private ICaptchaHandler captchaHandler;
    private ICaptchaGear captchaGear;

    public AbstractCaptcha setCaptchaHandler(ICaptchaHandler captchaHandler) {
        this.captchaHandler = captchaHandler;
        return this;
    }

    public AbstractCaptcha setCaptchaGear(ICaptchaGear captchaGear) {
        this.captchaGear = captchaGear;
        return this;
    }

    public ICaptchaHandler captchaHandler() {
        return this.captchaHandler;
    }

    public ICaptchaGear captchaGear() {
        return this.captchaGear;
    }

    @Override
    public Object build() {
        String cid = captchaHandler.buildId() + "_" + System.currentTimeMillis();
        CaptchaResult result = init(cid);
        cache(cid, result.getValue());
        return result.getClue();
    }

    @Override
    public void cache(String cid, String value) {
        captchaHandler.cache(cid, value);
    }

    @Override
    public void expire(String cid) {
        captchaHandler.expire(cid);
    }
}
