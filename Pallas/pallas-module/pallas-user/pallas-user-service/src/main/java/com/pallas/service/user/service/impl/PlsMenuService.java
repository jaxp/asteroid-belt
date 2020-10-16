package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsMenu;
import com.pallas.service.user.mapper.PlsMenuMapper;
import com.pallas.service.user.service.IPlsMenuService;
import org.springframework.stereotype.Service;

/**
 * @author: jax
 * @time: 2020/10/16 16:06
 * @desc: 菜单服务
 */
@Service
public class PlsMenuService extends ServiceImpl<PlsMenuMapper, PlsMenu> implements IPlsMenuService {
}
