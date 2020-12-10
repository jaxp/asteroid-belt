package com.pallas.service.user.bo;

import com.pallas.service.user.enums.OrganizationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/8/26 10:48
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsAuthorityBO {
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
    private Date addTime;
}
