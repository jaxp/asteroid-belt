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
     * @param organization
     * @return
     */
    Set<Long> getMenuIds(Long organization);

    /**
     * 获取菜单
     *
     * @param organization
     * @return
     */
    List<PlsMenuBO> getMenusWithPermission(Long organization);

    /**
     * 获取菜单
     *
     * @param organizations
     * @return
     */
    List<PlsMenuBO> getMenusWithPermission(Collection<Long> organizations);

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
