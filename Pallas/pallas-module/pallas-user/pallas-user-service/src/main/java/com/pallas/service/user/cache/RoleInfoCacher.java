package com.pallas.service.user.cache;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.cache.cacher.AbstractHashCacher;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.bean.PlsAuthoritySet;
import com.pallas.service.user.bean.PlsMenu;
import com.pallas.service.user.bean.PlsMenuSet;
import com.pallas.service.user.bean.PlsRole;
import com.pallas.service.user.enums.OrganizationType;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsAuthoritySetService;
import com.pallas.service.user.service.IPlsMenuService;
import com.pallas.service.user.service.IPlsMenuSetService;
import com.pallas.service.user.service.IPlsRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
        Map<String, String> result = new HashMap<>(3);
        if (Objects.nonNull(role)) {
            try {
                result.put(ROLE, objectMapper.writeValueAsString(role));

                List<PlsMenuSet> menuSets = plsMenuSetService.query()
                    .eq("organization", role.getId())
                    .eq("organization_type", OrganizationType.ROLE)
                    .list();
                List<PlsMenu> menus = plsMenuService.query()
                    .select("id")
                    .ge("grade", role.getGrade())
                    .list();
                Set<Long> menuIds = menuSets.stream().map(PlsMenuSet::getMenuId).collect(Collectors.toSet());
                menuIds.addAll(menus.stream().map(PlsMenu::getId).collect(Collectors.toSet()));
                result.put(MENUS, objectMapper.writeValueAsString(menuIds));

                List<PlsAuthoritySet> authoritySets = plsAuthoritySetService.query()
                    .eq("organization", role.getId())
                    .eq("organization_type", OrganizationType.ROLE)
                    .list();
                Set<Long> authIds = authoritySets.stream().map(PlsAuthoritySet::getAuthorityId).collect(Collectors.toSet());
                QueryChainWrapper<PlsAuthority> wrapper = plsAuthorityService.query()
                    .select("authority")
                    .ge("grade", role.getGrade());
                if (CollectionUtils.isNotEmpty(authIds)) {
                    wrapper.or().in("id", authIds);
                }
                List<PlsAuthority> plsAuthorities = wrapper.list();
                List<String> authorities = plsAuthorities.stream().map(PlsAuthority::getAuthority).collect(Collectors.toList());
                result.put(AUTHORITIES, objectMapper.writeValueAsString(authorities));
            } catch (JsonProcessingException e) {
                throw new PlsException("系统缓存加载失败");
            }
        }
        return result;
    }

    public Set<Long> getMenus() {
        String data = this.getData(MENUS);
        try {
            return objectMapper.readValue(data, new TypeReference<Set<Long>>(){});
        } catch (JsonProcessingException e) {
            throw new PlsException("系统缓存读取失败");
        }
    }

    public List<String> getAuthorities() {
        String data = this.getData(AUTHORITIES);
        try {
            return objectMapper.readValue(data, new TypeReference<List<String>>(){});
        } catch (JsonProcessingException e) {
            throw new PlsException("系统缓存读取失败");
        }
    }
}

