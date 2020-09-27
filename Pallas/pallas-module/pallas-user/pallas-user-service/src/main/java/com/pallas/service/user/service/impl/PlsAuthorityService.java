package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.bean.PlsUserAuthority;
import com.pallas.service.user.mapper.PlsAuthorityMapper;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsUserAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private IPlsUserAuthorityService plsUserAuthorityService;

    @Override
    public List<String> getAuthorities(long userId) {
        List<PlsUserAuthority> userAuthorities = plsUserAuthorityService.query()
            .eq("user_id", userId)
            .list();
        List<Long> authIds = userAuthorities.stream()
            .map(e -> e.getAuthorityId())
            .collect(Collectors.toList());
        List<PlsAuthority> plsAuthorities = query()
            .select("authority")
            .in("id", authIds)
            .eq("enabled", true)
            .list();
        List<String> authorities = plsAuthorities.stream()
            .map(e -> e.getAuthority())
            .collect(Collectors.toList());
        return authorities;
    }
}
