package com.pallas.service.user.service.impl;

import com.pallas.service.user.bean.PlsMenu;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.bo.PlsMenuBO;
import com.pallas.service.user.bo.PlsUserBO;
import com.pallas.service.user.cache.RoleInfoCacher;
import com.pallas.service.user.cache.TokenCacher;
import com.pallas.service.user.cache.UserInfoCacher;
import com.pallas.service.user.constant.Permission;
import com.pallas.service.user.constant.ResourceType;
import com.pallas.service.user.converter.PlsMenuConverter;
import com.pallas.service.user.converter.PlsUserConverter;
import com.pallas.service.user.properties.AuthProperties;
import com.pallas.service.user.service.IAuthService;
import com.pallas.service.user.service.IPlsMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/9/4 9:47
 * @desc: 会话相关
 */
@Slf4j
@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserInfoCacher userInfoCacher;
    @Autowired
    private TokenCacher tokenCacher;
    @Autowired
    private RoleInfoCacher roleInfoCacher;
    @Autowired
    private AuthProperties authProperties;
    @Autowired
    private IPlsMenuService plsMenuService;
    @Autowired
    private PlsMenuConverter plsMenuConverter;
    @Autowired
    private PlsUserConverter plsUserConverter;

    @Override
    public String login(PlsUser user) {
        return userInfoCacher.cacheUser(user, authProperties.getExpireTime());
    }

    @Override
    public void logout() {
        tokenCacher.clear();
        if (CollectionUtils.isEmpty(tokenCacher.tokenKeysOfSameUser())) {
            userInfoCacher.clear();
        }
    }

    @Override
    public PlsUserBO getUser() {
        return plsUserConverter.dto2bo(userInfoCacher.getUser());
    }

    @Override
    public List<PlsMenuBO> getMenus() {
        Map<Long, Integer> menuMap = getAuthorities(ResourceType.MENU);
        Set<Long> menuIds = menuMap.entrySet().stream()
            .filter(e -> !Permission.forbidden(e.getValue()))
            .map(e -> e.getKey())
            .collect(Collectors.toSet());
        List<PlsMenu> menus = plsMenuService.listByIds(menuIds);
        return plsMenuConverter.do2bo(menus);
    }

    @Override
    public Map<Long, Integer> getAuthorities(String resourceType) {
        Map<Long, Integer> authorities = userInfoCacher.getAuthorities(resourceType);
        Set<Long> forbiddenIds = authorities.entrySet().stream()
            .filter(e -> Permission.forbidden(e.getValue()))
            .map(e -> e.getKey())
            .collect(Collectors.toSet());
        // 角色权限
        Map<Long, Integer> roleMap = userInfoCacher.getAuthorities(ResourceType.ROLE);
        Set<Long> roleIds = roleMap.entrySet().stream()
            .filter(e -> !Permission.forbidden(e.getValue()))
            .map(e -> e.getKey())
            .collect(Collectors.toSet());
        for (Long roleId : roleIds) {
            Map<Long, Integer> roleAuthorityMap = roleInfoCacher.role(roleId)
                .getAuthorities(resourceType);
            for (Map.Entry<Long, Integer> entry : roleAuthorityMap.entrySet()) {
                Long key = entry.getKey();
                if (forbiddenIds.contains(key)) {
                    continue;
                }
                Integer value = entry.getValue();
                authorities.put(key, Optional.ofNullable(authorities.get(key)).orElse(0) | value);
            }
        }
        return authorities;
    }

    @Override
    public Boolean validate(String token) {
        return userInfoCacher.validate(token);
    }
}
