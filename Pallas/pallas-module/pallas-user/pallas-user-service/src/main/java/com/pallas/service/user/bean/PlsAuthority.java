package com.pallas.service.user.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pallas.service.user.enums.OrganizationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/8/26 10:38
 * @desc: 权限
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_u_authority")
public class PlsAuthority implements GrantedAuthority {

    @TableId
    private Long id;
    /**
     * 组织
     */
    private Long organization;
    /**
     * 组织类型
     */
    private OrganizationType organizationType;
    /**
     * 权限级别
     */
    private Integer permission;
    /**
     * 资源类型
     */
    private String resourceType;
    /**
     * 权限资源
     */
    private Long resource;
    /**
     * 标识
     */
    private String authority;
    /**
     * 新增用户
     */
    private Long addUser;
    /**
     * 新增时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

}
