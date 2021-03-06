package com.pallas.service.user.enums.menu;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: jax
 * @time: 2020/10/16 14:33
 * @desc: 菜单类型
 */
@Getter
@AllArgsConstructor
public enum MenuType {

    MENU(0, "菜单"),
    FOLDER(1, "菜单夹");

    @JsonValue
    @EnumValue
    private Integer value;
    private String desc;
}
