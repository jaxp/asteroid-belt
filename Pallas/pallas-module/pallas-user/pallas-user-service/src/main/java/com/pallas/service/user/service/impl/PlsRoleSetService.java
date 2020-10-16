package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsRoleSet;
import com.pallas.service.user.mapper.PlsRoleSetMapper;
import com.pallas.service.user.service.IPlsRoleSetService;
import org.springframework.stereotype.Service;

/**
 * @author: jax
 * @time: 2020/10/16 16:20
 * @desc: 角色设置服务
 */
@Service
public class PlsRoleSetService extends ServiceImpl<PlsRoleSetMapper, PlsRoleSet> implements IPlsRoleSetService {
}
