package com.pallas.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pallas.service.user.bean.PlsRole;

import java.util.Set;

/**
 * @author: jax
 * @time: 2020/10/16 16:05
 * @desc: 角色服务接口
 */
public interface IPlsRoleService extends IService<PlsRole> {
    /**
     * 获取角色
     *
     * @param userId
     * @return
     */
    Set<Long> getRoles(Long userId);
}
