package com.pallas.service.captcha.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: jax
 * @time: 2020/11/25 21:50
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResult {
    /**
     * 验证码线索
     */
    private Object clue;
    /**
     * 验证码结果（需缓存）
     */
    private String value;
}
