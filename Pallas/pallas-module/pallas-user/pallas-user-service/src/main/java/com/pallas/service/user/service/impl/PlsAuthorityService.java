package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.bean.PlsAuthoritySet;
import com.pallas.service.user.mapper.PlsAuthorityMapper;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsAuthoritySetService;
import com.pallas.service.user.service.IPlsRoleSetService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        List<PlsAuthoritySet> userAuthorities = plsAuthoritySetService.query()
            .eq("organization", userId)
            .list();
        if (CollectionUtils.isNotEmpty(userAuthorities)) {
            List<Long> authIds = userAuthorities.stream()
                .map(e -> e.getAuthorityId())
                .collect(Collectors.toList());
            List<PlsAuthority> plsAuthorities = plsAuthorityService.query()
                .select("authority")
                .in("id", authIds)
                .eq("enabled", true)
                .list();
            if (CollectionUtils.isNotEmpty(plsAuthorities)) {
                List<String> authorities = plsAuthorities.stream()
                    .map(e -> e.getAuthority())
                    .collect(Collectors.toList());
                return authorities;
            }
        }
        return new ArrayList<>();
    }

}
