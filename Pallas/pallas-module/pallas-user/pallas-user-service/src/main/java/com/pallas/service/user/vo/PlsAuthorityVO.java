package com.pallas.service.user.vo;

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
public class PlsAuthorityVO {
    private Long id;
    private Long organization;
    private OrganizationType organizationType;
    private Integer permission;
    private String resourceType;
    private Long resource;
    private String authority;
    private Long addUser;
    private Date addTime;
}
