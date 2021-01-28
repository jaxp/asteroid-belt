package com.pallas.service.user.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.cache.cache.AbstractHashCache;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.bean.PlsMenu;
import com.pallas.service.user.bean.PlsRole;
import com.pallas.service.user.constant.Permission;
import com.pallas.service.user.constant.ResourceType;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsMenuService;
import com.pallas.service.user.service.IPlsRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/10/19 22:06
 * @desc:
 */
@Component
public class RoleInfoCache extends AbstractHashCache<String> {

    private ThreadLocal<Long> context = new ThreadLocal<>();
    private static final String DATA = "data";

    @Autowired
    private IPlsRoleService plsRoleService;
    @Autowired
    private IPlsMenuService plsMenuService;
    @Autowired
    private IPlsAuthorityService plsAuthorityService;
    @Autowired
    private ObjectMapper objectMapper;

    public RoleInfoCache role(long roleId) {
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
        Map<String, String> result = new HashMap<>(3);
        if (Objects.nonNull(role)) {
            try {
                Map<String, List<PlsAuthority>> authorityMap =
                    plsAuthorityService.getAuthorityMap(role.getId());
                List<PlsMenu> menus = plsMenuService.query()
                    .select("id")
                    .ge("grade", role.getGrade())
                    .list();
                if (CollectionUtils.isNotEmpty(menus)) {
                    List<PlsAuthority> menuAuthorities = menus.stream()
                        .map(e -> new PlsAuthority()
                            .setResource(e.getId())
                            .setPermission(Permission.ALL)
                        ).collect(Collectors.toList());
                    if (authorityMap.containsKey(ResourceType.MENU)) {
                        menuAuthorities.addAll(authorityMap.get(ResourceType.MENU));
                    }
                    authorityMap.put(ResourceType.MENU, menuAuthorities);
                }
                result.put(DATA, objectMapper.writeValueAsString(role));
                for (Map.Entry<String, List<PlsAuthority>> entry : authorityMap.entrySet()) {
                    result.put(entry.getKey(), objectMapper.writeValueAsString(
                        entry.getValue().stream()
                            .collect(Collectors.toMap(PlsAuthority::getResource, PlsAuthority::getPermission))
                    ));
                }
            } catch (JsonProcessingException e) {
                throw new PlsException("系统缓存加载失败");
            }
        }
        return result;
    }

    public Map<Long, Integer> getAuthorities(String resourceType) {
        String data = this.getData(resourceType);
        try {
            return objectMapper.readValue(data, new TypeReference<Map<Long, Integer>>() {
            });
        } catch (JsonProcessingException e) {
            throw new PlsException("系统缓存读取失败");
        }
    }
}

