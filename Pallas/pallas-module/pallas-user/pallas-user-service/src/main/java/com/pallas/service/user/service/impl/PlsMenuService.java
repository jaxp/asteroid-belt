package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsMenu;
import com.pallas.service.user.bean.PlsMenuSet;
import com.pallas.service.user.bean.PlsRole;
import com.pallas.service.user.bean.PlsRoleSet;
import com.pallas.service.user.cache.UserInfoCacher;
import com.pallas.service.user.converter.PlsMenuConverter;
import com.pallas.service.user.dto.PlsMenuDTO;
import com.pallas.service.user.enums.Permission;
import com.pallas.service.user.mapper.PlsMenuMapper;
import com.pallas.service.user.service.IPlsMenuService;
import com.pallas.service.user.service.IPlsMenuSetService;
import com.pallas.service.user.service.IPlsRoleService;
import com.pallas.service.user.service.IPlsRoleSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Set<Long> getMenusIds(Long target) {
        List<PlsMenuSet> menusets = plsMenuSetService.query()
            .eq("target", target)
            .list();
        Set<Long> menuIds = menusets.stream().map(PlsMenuSet::getMenuId).collect(Collectors.toSet());
        return menuIds;
    }

    @Override
    public List<PlsMenuDTO> getUserMenus() {
        Long userId = userInfoCacher.getUserId();
        if (Objects.nonNull(userId)) {
            List<PlsRoleSet> roleSets = plsRoleSetService.query()
                .eq("user_id", userId)
                .list();
            Integer grade = null;
            List<Long> targets = new ArrayList<>();
            targets.add(userId);
            if (CollectionUtils.isNotEmpty(roleSets)) {
                // 获取角色
                Set<Long> roleIds = roleSets.stream()
                    .map(PlsRoleSet::getRoleId)
                    .collect(Collectors.toSet());
                List<PlsRole> roles = plsRoleService.query()
                    .in("id", roleIds)
                    .eq("enabled", true)
                    .list();
                // 获取绑定的菜单
                targets.addAll(roles.stream().map(PlsRole::getId).collect(Collectors.toSet()));
                // 获取角色等级下的菜单
                grade = roles.stream().map(PlsRole::getGrade).min(Integer::compareTo).get();
            }
            List<PlsMenuSet> menuSets = plsMenuSetService.query()
                .in("target", targets)
                .list();
            if (CollectionUtils.isEmpty(menuSets) && Objects.isNull(grade)) {
                return null;
            }
            QueryChainWrapper<PlsMenu> wrapper = query();
            Map<Long, Permission> menuSetMap = null;
            if (CollectionUtils.isNotEmpty(menuSets)) {
                menuSetMap = menuSets.stream()
                    .collect(Collectors.toMap(PlsMenuSet::getMenuId, PlsMenuSet::getPermission));
                wrapper.in("id", menuSetMap.keySet());
            }
            if (Objects.nonNull(grade)) {
                if (CollectionUtils.isNotEmpty(menuSets)) {
                    wrapper.or();
                }
                wrapper.ge("grade", grade);
            } else {
                grade = Integer.MAX_VALUE;
            }
            List<PlsMenu> menus = wrapper.list();
            List<PlsMenuDTO> menuDTOS = plsMenuConverter.do2dto(menus);
            for (PlsMenuDTO menuDTO : menuDTOS) {
                menuDTO.setPermission(Permission.QUERY);
                if (grade <= menuDTO.getGrade()) {
                    menuDTO.setPermission(Permission.EDIT_DELETE);
                }
                if (CollectionUtils.isNotEmpty(menuSetMap) && menuSetMap.containsKey(menuDTO.getId())) {
                    menuDTO.setPermission(menuSetMap.get(menuDTO.getId()));
                }
            }
            return menuDTOS;
        }
        return null;
    }
}
