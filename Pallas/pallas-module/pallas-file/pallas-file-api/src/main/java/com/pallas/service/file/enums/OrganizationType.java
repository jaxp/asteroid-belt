package com.pallas.service.file.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: jax
 * @time: 2020/11/26 13:31
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
