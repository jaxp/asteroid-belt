package com.pallas.service.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/10/21 16:54
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsMenuVO {
    private Long id;
    /**
     * 父级编号
     */
    private Long pid;
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
    private Integer type;
    /**
     * 菜单url
     */
    private String url;
    /**
     * 等级
     */
    private Integer rank;
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
    private Date addTime;
    /**
     * 更新时间
     */
    private Date updTime;
}
