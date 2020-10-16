package com.pallas.service.user.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pallas.service.user.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/8/26 10:54
 * @desc: 权限集
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_u_authority_set")
public class PlsAuthoritySet {
    @TableId
    private Long id;
    /**
     * 权限编号
     */
    private Long authorityId;
    /**
     * 目标编号
     */
    private Long target;
    /**
     * 目标类型
     */
    private TargetType targetType;
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
