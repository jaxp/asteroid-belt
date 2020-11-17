package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsMenu;
import com.pallas.service.user.bean.PlsMenuSet;
import com.pallas.service.user.bo.PlsMenuBO;
import com.pallas.service.user.cache.UserInfoCacher;
import com.pallas.service.user.converter.PlsMenuConverter;
import com.pallas.service.user.enums.Permission;
import com.pallas.service.user.mapper.PlsMenuMapper;
import com.pallas.service.user.service.IPlsMenuService;
import com.pallas.service.user.service.IPlsMenuSetService;
import com.pallas.service.user.service.IPlsRoleService;
import com.pallas.service.user.service.IPlsRoleSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/10/16 16:06
 * @desc: 菜单服务
 */
@Service
public class PlsMenuService extends ServiceImpl<PlsMenuMapper, PlsMenu> implements IPlsMenuService {

    @Autowired
    private IPlsMenuSetService plsMenuSetService;
    @Autowired
    private IPlsRoleService plsRoleService;
    @Autowired
    private IPlsRoleSetService plsRoleSetService;
    @Autowired
    private UserInfoCacher userInfoCacher;
    @Autowired
    private PlsMenuConverter plsMenuConverter;

    @Override
    public Set<Long> getMenuIds(Long target) {
        List<PlsMenuSet> menusets = plsMenuSetService.query()
            .eq("target", target)
            .list();
        Set<Long> menuIds = menusets.stream().map(PlsMenuSet::getMenuId).collect(Collectors.toSet());
        return menuIds;
    }

    @Override
    public List<PlsMenuBO> getMenusWithPermission(Long target) {
        return getMenusWithPermission(Arrays.asList(target));
    }

    @Override
    public List<PlsMenuBO> getMenusWithPermission(Collection<Long> targets) {
        if (CollectionUtils.isEmpty(targets)) {
            return new ArrayList<>();
        }
        List<PlsMenuSet> menusets = plsMenuSetService.query()
            .in("target", targets)
            .list();
        if (CollectionUtils.isEmpty(menusets)) {
            return new ArrayList<>();
        }
        Map<Long, Permission> menuIdPermissionMap = menusets.stream()
            .collect(Collectors.toMap(PlsMenuSet::getMenuId, PlsMenuSet::getPermission));
        List<PlsMenu> menus = listByIds(menuIdPermissionMap.values());
        List<PlsMenuBO> menuBOS = plsMenuConverter.do2bo(menus);
        menuBOS.forEach(e -> e.loadPermission(menuIdPermissionMap));
        return menuBOS;
    }

    @Override
    public List<PlsMenuBO> getGradeMenus(Integer grade) {
        if (Objects.isNull(grade)) {
            return new ArrayList<>();
        }
        List<PlsMenu> menus = query()
            .ge("grade", grade)
            .list();
        List<PlsMenuBO> menuBOS = plsMenuConverter.do2bo(menus);
        menuBOS.forEach(e -> e.setPermission(Permission.EDIT_DELETE));
        return menuBOS;
    }

    @Override
    public boolean exists(Long id) {
        return false;
    }
}
