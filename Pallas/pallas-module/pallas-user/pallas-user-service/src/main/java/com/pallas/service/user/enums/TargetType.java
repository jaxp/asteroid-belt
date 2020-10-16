package com.pallas.service.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: jax
 * @time: 2020/10/16 15:43
 * @desc:
 */
@Getter
@AllArgsConstructor
public enum TargetType {

    USER(0, "用户"),
    ROLE(1, "角色");

    private Integer value;
    private String desc;
}
