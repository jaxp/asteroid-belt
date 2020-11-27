package com.pallas.service.file.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: jax
 * @time: 2020/11/26 13:17
 * @desc:
 */
@Getter
@AllArgsConstructor
public enum Sensitive {

    ANOYMOUS(0, "匿名"),
    AUTHORIZED(1, "登录"),
    ORGANIZATION(2, "组织");

    private Integer value;
    private String desc;
}
