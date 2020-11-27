package com.pallas.service.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: jax
 * @time: 2020/10/16 15:43
 * @desc:
 */
@Getter
@AllArgsConstructor
public enum OrganizationType {

    USER(0, "用户"),
    ROLE(1, "角色");

    @JsonValue
    @EnumValue
    private Integer value;
    private String desc;
}
