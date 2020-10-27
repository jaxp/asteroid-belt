package com.pallas.service.user.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: jax
 * @time: 2020/10/27 8:14
 * @desc:
 */
@Getter
@AllArgsConstructor
public enum Permission {
    QUERY(0, "查询"),
    EDIT(1, "编辑"),
    EDIT_DELETE(2, "编辑和删除");

    @JsonValue
    @EnumValue
    private Integer value;
    private String desc;
}
