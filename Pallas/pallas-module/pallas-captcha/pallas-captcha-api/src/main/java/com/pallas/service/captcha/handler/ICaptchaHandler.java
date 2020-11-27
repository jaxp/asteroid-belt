package com.pallas.service.captcha.handler;

/**
 * @author: jax
 * @time: 2020/11/25 21:38
 * @desc: 验证码处理器接口
 */
public interface ICaptchaHandler {
    /**
     * 生成唯一键
     *
     * @return
     */
    String buildId();

    /**
     * 缓存验证码
     *
     * @param checkCid
     */
    void cache(String checkCid, String value);

    /**
     * 获取验证码凭证
     *
     * @param checkCid
     */
    String getCache(String checkCid);

    /**
     * 过期验证码
     *
     * @param checkCid
     */
    void expire(String checkCid);

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
