package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.cache.UserInfoCacher;
import com.pallas.service.user.constant.Permission;
import com.pallas.service.user.enums.OrganizationType;
import com.pallas.service.user.mapper.PlsAuthorityMapper;
import com.pallas.service.user.service.IPlsAuthorityService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/8/26 11:22
 * @desc:
 */
@Service
public class PlsAuthorityService extends ServiceImpl<PlsAuthorityMapper, PlsAuthority> implements IPlsAuthorityService {

    @Autowired
    private UserInfoCacher userInfoCacher;

    @Override
    public List<PlsAuthority> getAllAuthorities(long organization) {
        return query().eq("organization", organization)
            .list();
    }

    @Override
    public Map<String, List<PlsAuthority>> getAuthorityMap(long organization) {
        List<PlsAuthority> authorities = this.getAllAuthorities(organization);
        return authorities.stream()
            .collect(Collectors.groupingBy(PlsAuthority::getResourceType));
    }

    @Override
    public List<PlsAuthority> getAuthorities(Long organization, String resourceTYpe) {
        return query().eq("organization", organization)
            .eq("resource_type", resourceTYpe)
            .list();
    }

    @Override
    public List<PlsAuthority> getAllAuthorities(Collection<Long> organizations) {
        if (CollectionUtils.isNotEmpty(organizations)) {
            return query().in("organization", organizations)
                .list();
        }
        return new ArrayList<>();
    }

    @Override
    public Map<String, List<PlsAuthority>> getAuthorityMap(Collection<Long> organizations) {
        List<PlsAuthority> authorities = this.getAllAuthorities(organizations);
        return authorities.stream()
            .collect(Collectors.groupingBy(PlsAuthority::getResourceType));
    }

    @Override
    public List<PlsAuthority> getAuthorities(Collection<Long> organizations, String resourceTYpe) {
        return query().in("organization", organizations)
            .eq("resource_type", resourceTYpe)
            .list();
    }

    @Override
    public Integer getAuthority(Long organization, String resourceType, Long resource) {
        PlsAuthority authority = query()
            .select("permission")
            .eq("organization", organization)
            .eq("resource", resource)
            .eq("resource_type", resourceType)
            .one();
        return authority.getPermission();
    }

    @Override
    public Integer getAuthority(String resourceType, Long resource) {
        Set<Long> organizations = userInfoCacher.getRoles();
        Long userId = userInfoCacher.getUserId();
        organizations.add(userId);
        List<PlsAuthority> auths = query()
            .select("organization_type", "permission")
            .eq("resource", resource)
            .in("organization", organizations)
            .eq("resource_type", resourceType)
            .list();
        Optional<OrganizationType> minOrganizationType = auths.stream()
            .map(PlsAuthority::getOrganizationType)
            .min(Comparator.comparing(e -> e));
        if (minOrganizationType.isPresent()) {
            Integer permission = auths.stream()
                .filter(e -> Objects.equals(e.getOrganizationType(), minOrganizationType.get()))
                .map(PlsAuthority::getPermission)
                .reduce(Permission.NONE, (acc, element) -> acc | element);
            return permission;
        }
        return Permission.NONE;
    }

    @Override
    public Map<Long, Integer> getAuthorityMap(Set<Long> resources) {
        Set<Long> organizations = userInfoCacher.getRoles();
        Long userId = userInfoCacher.getUserId();
        organizations.add(userId);
        List<PlsAuthority> auths = query()
            .select("resource", "organization_type", "permission")
            .in("resource", resources)
            .in("organization", organizations)
            .list();
        Map<Long, OrganizationType> organizationTypeMap = auths.stream()
            .collect(Collectors.toMap(PlsAuthority::getResource, PlsAuthority::getOrganizationType,
                BinaryOperator.minBy(Comparator.comparing(Function.identity()))));
        Map<Long, Integer> permissionMap;
        if (!organizationTypeMap.isEmpty()) {
            permissionMap = auths.stream()
                .filter(e -> organizationTypeMap.containsKey(e.getResource()) &&
                    Objects.equals(e.getOrganizationType(), organizationTypeMap.get(e.getResource())))
                .collect(Collectors.toMap(PlsAuthority::getResource, PlsAuthority::getPermission,
                    (a, b) -> a | b));
        } else {
            permissionMap = new HashMap<>();
        }
        resources.forEach(e -> {
            if (!permissionMap.containsKey(e)) {
                permissionMap.put(e, Permission.NONE);
            }
        });
        return permissionMap;
    }
}
