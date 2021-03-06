package com.pallas.service.captcha.builder;

import com.pallas.service.captcha.entity.CaptchaClue;
import com.pallas.service.captcha.entity.CaptchaResult;

/**
 * @author: jax
 * @time: 2020/11/25 21:34
 * @desc: 验证码接口
 */
public interface ICaptcha {
    /**
     * 构建验证码
     *
     * @param cid
     * @return
     */
    CaptchaClue build();

    /**
     * 初始化验证码
     */
    CaptchaResult init();

    /**
     * 缓存校验id
     *
     * @param result
     */
    void cache(CaptchaResult result);

    /**
     * 校验验证码
     *
     * @param cid
     * @param checkCode
     * @return 验证通过凭证
     */
    String check(String cid, String checkCode, boolean enable);

    /**
     * 过期操作
     *
     * @param cid
     */
    void expire(String cid);
}
