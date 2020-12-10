package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.mapper.PlsAuthorityMapper;
import com.pallas.service.user.service.IPlsAuthorityService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/8/26 11:22
 * @desc:
 */
@Service
public class PlsAuthorityService extends ServiceImpl<PlsAuthorityMapper, PlsAuthority> implements IPlsAuthorityService {

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
}
