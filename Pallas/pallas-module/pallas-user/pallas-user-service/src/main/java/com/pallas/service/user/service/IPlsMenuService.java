package com.pallas.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pallas.service.user.bean.PlsMenu;
import com.pallas.service.user.bo.PlsMenuBO;

import java.util.Collection;
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
    Set<Long> getMenuIds(Long target);

    /**
     * 获取菜单
     *
     * @param target
     * @return
     */
    List<PlsMenuBO> getMenusWithPermission(Long target);

    /**
     * 获取菜单
     *
     * @param targets
     * @return
     */
    List<PlsMenuBO> getMenusWithPermission(Collection<Long> targets);

    /**
     * 获取等级对应的菜单
     *
     * @param grade
     * @return
     */
    List<PlsMenuBO> getGradeMenus(Integer grade);

    /**
     * 菜单存在
     *
     * @param id
     * @return
     */
    boolean exists(Long id);

}
