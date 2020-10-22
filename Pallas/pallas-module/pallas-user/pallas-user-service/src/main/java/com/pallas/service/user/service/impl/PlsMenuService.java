package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsMenu;
import com.pallas.service.user.bean.PlsMenuSet;
import com.pallas.service.user.mapper.PlsMenuMapper;
import com.pallas.service.user.service.IPlsMenuService;
import com.pallas.service.user.service.IPlsMenuSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/10/16 16:06
 * @desc: 菜单服务
 */
@Service
public class PlsMenuService extends ServiceImpl<PlsMenuMapper, PlsMenu> implements IPlsMenuService {

    @Autowired
    private IPlsMenuSetService plsMenuSetService;

    @Override
    public Set<Long> getMenus(Long target) {
        List<PlsMenuSet> menusets = plsMenuSetService.query()
            .eq("target", target)
            .list();
        Set<Long> menuIds = menusets.stream().map(PlsMenuSet::getMenuId).collect(Collectors.toSet());
        return menuIds;
    }
}
