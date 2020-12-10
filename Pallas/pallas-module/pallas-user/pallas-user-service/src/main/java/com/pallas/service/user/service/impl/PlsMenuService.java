package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.bean.PlsMenu;
import com.pallas.service.user.bo.PlsMenuBO;
import com.pallas.service.user.constant.Permission;
import com.pallas.service.user.constant.ResourceType;
import com.pallas.service.user.converter.PlsMenuConverter;
import com.pallas.service.user.mapper.PlsMenuMapper;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/10/16 16:06
 * @desc: 菜单服务
 */
@Service
public class PlsMenuService extends ServiceImpl<PlsMenuMapper, PlsMenu> implements IPlsMenuService {

    @Autowired
    private PlsMenuConverter plsMenuConverter;
    @Autowired
    private IPlsAuthorityService plsAuthorityService;

    @Override
    public Map<Long, Integer> getMenus(Long organization) {
        List<PlsAuthority> authorities = plsAuthorityService.getAuthorities(organization, ResourceType.MENU);
        return authorities.stream()
            .collect(Collectors.toMap(PlsAuthority::getResource, PlsAuthority::getPermission));
    }

    @Override
    public List<PlsMenuBO> getMenusWithPermission(Long organization) {
        return getMenusWithPermission(Arrays.asList(organization));
    }

    @Override
    public List<PlsMenuBO> getMenusWithPermission(Collection<Long> organizations) {
        if (CollectionUtils.isEmpty(organizations)) {
            return new ArrayList<>();
        }
        List<PlsAuthority> authorities = plsAuthorityService.getAuthorities(organizations, ResourceType.MENU);
        if (CollectionUtils.isEmpty(authorities)) {
            return new ArrayList<>();
        }
        Map<Long, Integer> menuIdPermissionMap = authorities.stream()
            .collect(Collectors.toMap(PlsAuthority::getResource, PlsAuthority::getPermission));
        List<PlsMenu> menus = listByIds(menuIdPermissionMap.keySet());
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
        menuBOS.forEach(e -> e.setPermission(Permission.ALL));
        return menuBOS;
    }

    @Override
    public boolean exists(Long id) {
        return false;
    }
}
