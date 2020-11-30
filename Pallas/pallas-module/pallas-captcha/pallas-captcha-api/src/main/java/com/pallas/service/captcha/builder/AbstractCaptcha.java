package com.pallas.service.captcha.builder;

import com.pallas.service.captcha.entity.CaptchaClue;
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

    public String getCid() {
        return captchaHandler.buildId() + "_" + System.currentTimeMillis();
    }

    @Override
    public CaptchaClue build() {
        // 生成验证码
        CaptchaResult result = init();
        // 保存相关图片
        captchaHandler.saveImages(result);
        // 缓存验证码结果
        cache(result);
        return result.getClue();
    }

    @Override
    public void cache(CaptchaResult result) {
        captchaHandler.cache(result.getClue().getCid(), result.getValue());
    }

    @Override
    public void expire(String cid) {
        captchaHandler.expire(cid);
    }
}
