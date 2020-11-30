package com.pallas.service.captcha.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * @author: jax
 * @time: 2020/11/27 23:13
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaClue {
    /**
     * 验证码唯一键
     */
    private String cid;
    /**
     * 图片
     */
    private List<Long> images;
    /**
     * 数据
     */
    private Map<String, Object> data;
}
