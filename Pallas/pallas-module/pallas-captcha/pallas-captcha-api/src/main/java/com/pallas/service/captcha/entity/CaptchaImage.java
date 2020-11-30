package com.pallas.service.captcha.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: jax
 * @time: 2020/11/27 21:50
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaImage {

    /**
     * 内容
     */
    private byte[] content;
    /**
     * 文件名
     */
    private String fileName;
}
