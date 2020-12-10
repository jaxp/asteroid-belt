package com.pallas.service.user.bo;

import com.pallas.common.utils.SpringUtils;
import com.pallas.service.user.constant.Permission;
import com.pallas.service.user.service.IPlsMenuService;
import com.pallas.service.user.service.IPlsRoleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/8/24 15:07
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsUserBO {
    private Long id;
    private String username;
    private String telephone;
    private String email;
    private Date addTime;
    private Date updTime;
    private Boolean enabled;
    private Boolean accountExpired;
    private Boolean accountLocked;
    private Boolean pwdExpired;
    private List<String> authorities;

    private List<PlsRoleBO> roles;
    private Integer grade;

    private List<PlsMenuBO> userMenus;
    private List<PlsMenuBO> gradeMenus;
    private List<PlsMenuBO> roleMenus;
    private List<PlsMenuBO> menus;

    private Map<Long, Integer> permissionMap;

    private IPlsRoleService roleService;
    private IPlsMenuService menuService;

    public PlsUserBO init() {
        if (Objects.nonNull(this.id)) {
            this.menuService = SpringUtils.getBean("plsMenuService", IPlsMenuService.class);
            this.roleService = SpringUtils.getBean("plsRoleService", IPlsRoleService.class);
            this.userMenus = this.menuService.getMenusWithPermission(this.id);
            // 获取角色信息
            this.roles = this.roleService.getRoles(this.id);
            this.grade = roles.stream().map(PlsRoleBO::getGrade).min(Integer::compareTo).get();
        }
        return this;
    }

    public List<PlsMenuBO> getMenus() {
        this.init();
        if (Objects.isNull(menus)) {
            // 等级菜单
            this.gradeMenus = this.menuService.getGradeMenus(this.grade);
            // 角色菜单
            Set<Long> roleIds = roles.stream()
                .map(PlsRoleBO::getId)
                .collect(Collectors.toSet());
            this.roleMenus = this.menuService.getMenusWithPermission(roleIds);
            // 优先级：用户菜单>角色菜单>等级菜单
            Map<Long, PlsMenuBO> menuMap = this.gradeMenus.stream()
                .collect(Collectors.toMap(PlsMenuBO::getId, e -> e));
            this.roleMenus.forEach(e -> menuMap.put(e.getId(), e));
            this.userMenus.forEach(e -> menuMap.put(e.getId(), e));
            this.menus = new ArrayList<>(menuMap.values());
        }
        return this.menus;
    }

    public int getPermission(Long menuId) {
        return Optional.ofNullable(this.getPermissionMap().get(menuId)).orElse(Permission.NONE);
    }

    private Map<Long, Integer> getPermissionMap() {
        if (Objects.isNull(permissionMap)) {
            this.permissionMap = this.getMenus().stream()
                .collect(Collectors.toMap(PlsMenuBO::getId, PlsMenuBO::getPermission));
        }
        return this.permissionMap;
    }
}
