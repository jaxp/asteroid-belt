package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsMenuSet;
import com.pallas.service.user.mapper.PlsMenuSetMapper;
import com.pallas.service.user.service.IPlsMenuSetService;
import org.springframework.stereotype.Service;

/**
 * @author: jax
 * @time: 2020/10/16 16:06
 * @desc: 菜单设置服务
 */
@Service
public class PlsMenuSetService extends ServiceImpl<PlsMenuSetMapper, PlsMenuSet> implements IPlsMenuSetService {
}
