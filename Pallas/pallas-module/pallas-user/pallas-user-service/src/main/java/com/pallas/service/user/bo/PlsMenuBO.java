package com.pallas.service.user.bo;

import com.pallas.service.user.enums.Permission;
import com.pallas.service.user.enums.menu.MenuType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Map;

/**
 * @author: jax
 * @time: 2020/10/21 16:53
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsMenuBO {
    private Long id;
    private Long pid;
    private String title;
    private String icon;
    private Boolean enabled;
    private Boolean disabled;
    private MenuType type;
    private String url;
    private Permission permission;
    private Integer grade;
    private Long addUser;
    private Long updUser;
    private Date addTime;
    private Date updTime;

    public void loadPermission(Map<Long, Permission> permissionMap) {
        this.permission = permissionMap.get(this.id);
    }
}
