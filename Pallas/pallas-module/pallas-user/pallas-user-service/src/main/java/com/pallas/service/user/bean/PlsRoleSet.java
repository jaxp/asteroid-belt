package com.pallas.service.user.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/10/16 15:41
 * @desc: 角色权限设置
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_u_role_set")
public class PlsRoleSet {
    @TableId
    private Long id;
    /**
     * 角色编号
     */
    private Long roleId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 添加用户
     */
    private Long addUser;
    /**
     * 新增时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;
}
