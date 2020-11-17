package com.pallas.service.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/11/17 17:39
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsRoleVO {
    private Long id;
    /**
     * 父角色
     */
    private Long pid;
    /**
     * 角色
     */
    private String role;
    /**
     * 名称
     */
    private String name;
    /**
     * 等级
     */
    private Integer grade;
    /**
     * 是否可用
     */
    private Boolean enabled;
    /**
     * 新增时间
     */
    private Date addTime;
    /**
     * 更新时间
     */
    private Date updTime;
}
