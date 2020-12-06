package com.pallas.service.captcha.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jax
 * @time: 2020/12/4 17:38
 * @desc:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaParams {
    private String cid;
    private String code;
}
