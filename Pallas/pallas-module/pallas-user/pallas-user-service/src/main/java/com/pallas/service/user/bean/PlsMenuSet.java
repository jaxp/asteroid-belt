package com.pallas.service.user.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pallas.service.user.enums.Permission;
import com.pallas.service.user.enums.OrganizationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/10/16 15:41
 * @desc: 菜单权限设置
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_u_menu_set")
public class PlsMenuSet {
    @TableId
    private Long id;
    /**
     * 菜单编号
     */
    private Long menuId;
    /**
     * 组织
     */
    private Long organization;
    /**
     * 组织类型
     */
    private OrganizationType organizationType;
    /**
     * 权限类别
     */
    private Permission permission;
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
