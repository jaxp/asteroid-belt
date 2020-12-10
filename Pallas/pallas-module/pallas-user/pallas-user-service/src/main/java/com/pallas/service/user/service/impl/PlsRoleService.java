package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.bean.PlsRole;
import com.pallas.service.user.bo.PlsRoleBO;
import com.pallas.service.user.constant.Permission;
import com.pallas.service.user.constant.ResourceType;
import com.pallas.service.user.converter.PlsRoleConverter;
import com.pallas.service.user.mapper.PlsRoleMapper;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/10/16 16:19
 * @desc: 角色服务
 */
@Service
public class PlsRoleService extends ServiceImpl<PlsRoleMapper, PlsRole> implements IPlsRoleService {

    @Autowired
    private IPlsAuthorityService plsAuthorityService;
    @Autowired
    private PlsRoleConverter roleConverter;

    @Override
    public Map<Long, Integer> getUserRoles(Long userId) {
        List<PlsAuthority> authorities = plsAuthorityService.getAuthorities(userId, ResourceType.ROLE);
        return authorities.stream()
            .collect(Collectors.toMap(PlsAuthority::getResource, PlsAuthority::getPermission));
    }

    @Override
    public Set<Long> validRoleIds(Long userId) {
        List<PlsAuthority> authorities = plsAuthorityService.getAuthorities(userId, ResourceType.ROLE);
        return authorities.stream()
            .filter(e -> !Permission.forbidden(e.getPermission()))
            .map(PlsAuthority::getResource)
            .collect(Collectors.toSet());
    }

    @Override
    public List<PlsRoleBO> getRoles(Long userId) {
        List<PlsRole> roles = query()
            .in("id", validRoleIds(userId))
            .eq("enabled", true)
            .list();
        return roleConverter.do2bo(roles);
    }
}
