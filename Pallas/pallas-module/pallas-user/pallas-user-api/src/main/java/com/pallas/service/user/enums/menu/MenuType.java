package com.pallas.service.user.enums.menu;

import com.baomidou.mybatisplus.annotation.EnumValue;
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

    @EnumValue
    private Integer value;
    private String desc;
}
