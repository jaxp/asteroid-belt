package com.pallas.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/8/13 16:52
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsUserDTO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 新增时间
     */
    private Date addTime;
    /**
     * 更新时间
     */
    private Date updTime;
    /**
     * 是否有效
     */
    private Boolean enabled;
    /**
     * 账号过期
     */
    private Boolean accountExpired;
    /**
     * 账号锁定
     */
    private Boolean accountLocked;
    /**
     * 密码过期
     */
    private Boolean pwdExpired;
}
