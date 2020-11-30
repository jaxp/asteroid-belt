package com.pallas.service.captcha.handler;

import com.pallas.service.captcha.builder.AbstractCaptcha;
import com.pallas.service.captcha.builder.ICaptcha;
import com.pallas.service.captcha.entity.CaptchaResult;

/**
 * @author: jax
 * @time: 2020/11/25 21:38
 * @desc: 验证码处理器接口
 */
public interface ICaptchaHandler {
    /**
     * 构建验证码
     *
     * @param clazz
     * @return
     */
    ICaptcha build(Class<? extends AbstractCaptcha> clazz);

    /**
     * 生成唯一键
     *
     * @return
     */
    String buildId();

    /**
     * 保存图片
     *
     * @param captchaResult
     * @return
     */
    void saveImages(CaptchaResult captchaResult);

    /**
     * 缓存验证码
     *
     * @param cid
     */
    void cache(String cid, String value);

    /**
     * 获取验证码凭证
     *
     * @param cid
     */
    String getCache(String cid);

    /**
     * 过期验证码
     *
     * @param cid
     */
    void expire(String cid);

    /**
     * 缓存授权
     *
     * @param certicertificate
     */
    void cacheCertificate(String certificate);

    /**
     * 获取授权
     *
     * @param certificate
     */
    boolean existCertificate(String certificate);

    /**
     * 删除授权
     */
    void expireCertificate(String certificate);
}
