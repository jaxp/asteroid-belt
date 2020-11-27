package com.pallas.service.captcha.handler;

/**
 * @author: jax
 * @time: 2020/11/25 22:05
 * @desc: 控制验证码是否启用及特殊情况处理
 */
public interface ICaptchaGear {

    /**
     * 是否开启验证码校验功能
     *
     * @return
     */
    boolean enable();

    /**
     * 图形清晰度
     *
     * @return
     */
    default float definition() {
        return 1.5f;
    }

    /**
     * 是否针对特定场景关闭验证码
     *
     * @param cid
     * @return
     */
    default boolean enable(String cid) {
        return true;
    }
}
