package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsRole;
import com.pallas.service.user.mapper.PlsRoleMapper;
import com.pallas.service.user.service.IPlsRoleService;
import org.springframework.stereotype.Service;

/**
 * @author: jax
 * @time: 2020/10/16 16:19
 * @desc: 角色服务
 */
@Service
public class PlsRoleService extends ServiceImpl<PlsRoleMapper, PlsRole> implements IPlsRoleService {
}
