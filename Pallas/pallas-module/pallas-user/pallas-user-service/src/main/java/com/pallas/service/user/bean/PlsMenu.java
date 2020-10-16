package com.pallas.service.user.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pallas.service.user.enums.menu.MenuType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/10/16 14:06
 * @desc: 菜单bean
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_u_menu")
public class PlsMenu {
    @TableId
    private Long id;
    /**
     * 父级编号
     */
    private Long pId;
    /**
     * 菜单名
     */
    private String title;
    /**
     * 菜单icon
     */
    private String icon;
    /**
     * 是否可用
     */
    private Boolean enabled;
    /**
     * 是否显示禁用
     */
    private Boolean disabled;
    /**
     * 菜单类型
     */
    private MenuType type;
    /**
     * 菜单url
     */
    private String url;
    /**
     * 添加用户
     */
    private Long addUser;
    /**
     * 更新用户
     */
    private Long updUser;
    /**
     * 添加时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updTime;
}
