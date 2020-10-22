package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsRole;
import com.pallas.service.user.bean.PlsRoleSet;
import com.pallas.service.user.mapper.PlsRoleMapper;
import com.pallas.service.user.service.IPlsRoleService;
import com.pallas.service.user.service.IPlsRoleSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private IPlsRoleSetService plsRoleSetService;

    @Override
    public Set<Long> getRoles(Long userId) {
        List<PlsRoleSet> roleSets = plsRoleSetService.query()
            .eq("user_id", userId)
            .list();
        Set<Long> roleIds = roleSets.stream().map(PlsRoleSet::getRoleId).collect(Collectors.toSet());
        return roleIds;
    }
}
