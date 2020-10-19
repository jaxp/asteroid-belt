package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.bean.PlsAuthoritySet;
import com.pallas.service.user.bean.PlsRoleSet;
import com.pallas.service.user.mapper.PlsAuthorityMapper;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsAuthoritySetService;
import com.pallas.service.user.service.IPlsRoleSetService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/8/26 11:22
 * @desc:
 */
@Service
public class PlsAuthorityService extends ServiceImpl<PlsAuthorityMapper, PlsAuthority> implements IPlsAuthorityService {

    @Autowired
    private IPlsRoleSetService plsRoleSetService;
    @Autowired
    private IPlsAuthoritySetService plsAuthoritySetService;
    @Autowired
    private IPlsAuthorityService plsAuthorityService;

    @Override
    public List<String> getAuthorities(long userId) {
        List<PlsAuthority> plsAuthorities = this.authorities(userId);
        if (CollectionUtils.isNotEmpty(plsAuthorities)) {
            List<String> authorities = plsAuthorities.stream()
                .map(e -> e.getAuthority())
                .collect(Collectors.toList());
            return authorities;
        }
        return new ArrayList<>();
    }

    @Override
    public List<PlsAuthority> authorities(long userId) {
        List<PlsRoleSet> roleSets = plsRoleSetService.query()
            .eq("user_id", userId)
            .list();
        Set<Long> targets = roleSets.stream()
            .map(PlsRoleSet::getRoleId)
            .collect(Collectors.toSet());
        targets.add(userId);
        List<PlsAuthoritySet> userAuthorities = plsAuthoritySetService.query()
            .in("target", targets)
            .list();
        if (CollectionUtils.isNotEmpty(userAuthorities)) {
            List<Long> authIds = userAuthorities.stream()
                .map(e -> e.getAuthorityId())
                .collect(Collectors.toList());
            List<PlsAuthority> authorities = plsAuthorityService.query()
                .select("authority")
                .in("id", authIds)
                .eq("enabled", true)
                .list();
            return authorities;
        }
        return null;
    }
}
