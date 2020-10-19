package com.pallas.service.user.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.cache.cacher.AbstractHashCacher;
import com.pallas.service.user.bean.*;
import com.pallas.service.user.enums.TargetType;
import com.pallas.service.user.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/10/19 22:06
 * @desc:
 */
@Component
public class RoleInfoCacher extends AbstractHashCacher<String> {

    private ThreadLocal<Long> context = new ThreadLocal<>();
    private static final String ROLE = "role";
    private static final String MENUS = "menus";
    private static final String AUTHORITIES = "authorities";

    @Autowired
    private IPlsRoleService plsRoleService;
    @Autowired
    private IPlsMenuService plsMenuService;
    @Autowired
    private IPlsMenuSetService plsMenuSetService;
    @Autowired
    private IPlsAuthorityService plsAuthorityService;
    @Autowired
    private IPlsAuthoritySetService plsAuthoritySetService;
    @Autowired
    private ObjectMapper objectMapper;

    public RoleInfoCacher role(long roleId) {
        this.context.set(roleId);
        return this;
    }

    public Long getRole() {
        if (Objects.isNull(context.get())) {
            throw new PlsException("未设置角色");
        }
        return context.get();
    }

    @Override
    public String getKey() {
        return PlsConstant.ROLE_INFO_KEY + getRole();
    }

    @Override
    public Map<String, String> loadData() {
        PlsRole role = plsRoleService.getById(context.get());
        if (Objects.nonNull(role)) {
            Map<String, String> result = new HashMap<>();
            try {
                result.put(ROLE, objectMapper.writeValueAsString(role));
                List<PlsMenuSet> menuSets = plsMenuSetService.query()
                    .eq("target", role.getId())
                    .eq("target_type", TargetType.ROLE)
                    .list();
                if (CollectionUtils.isNotEmpty(menuSets)) {
                    Set<Long> menuIds = menuSets.stream().map(PlsMenuSet::getMenuId).collect(Collectors.toSet());
                    List<PlsMenu> menus = plsMenuService.listByIds(menuIds);
                    result.put(MENUS, objectMapper.writeValueAsString(menus));
                }
                List<PlsAuthoritySet> authoritySets = plsAuthoritySetService.query()
                    .eq("target", role.getId())
                    .eq("target_type", TargetType.ROLE)
                    .list();
                if (CollectionUtils.isNotEmpty(authoritySets)) {
                    Set<Long> authIds = authoritySets.stream().map(PlsAuthoritySet::getAuthorityId).collect(Collectors.toSet());
                    List<PlsAuthority> authorities = plsAuthorityService.listByIds(authIds);
                    result.put(AUTHORITIES, objectMapper.writeValueAsString(authorities));
                }
            } catch (JsonProcessingException e) {
                throw new PlsException("系统缓存加载失败");
            }
        }
        return null;
    }
}

