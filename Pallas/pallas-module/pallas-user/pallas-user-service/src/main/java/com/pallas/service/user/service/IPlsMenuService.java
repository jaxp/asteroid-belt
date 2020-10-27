package com.pallas.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pallas.service.user.bean.PlsMenu;
import com.pallas.service.user.dto.PlsMenuDTO;

import java.util.List;
import java.util.Set;

/**
 * @author: jax
 * @time: 2020/10/16 16:03
 * @desc: 菜单服务接口
 */
public interface IPlsMenuService extends IService<PlsMenu> {
    /**
     * 获取菜单
     *
     * @param target
     * @return
     */
    Set<Long> getMenusIds(Long target);

    /**
     * 获取可编辑菜单
     *
     * @return
     */
    List<PlsMenuDTO> getUserMenus();

}
