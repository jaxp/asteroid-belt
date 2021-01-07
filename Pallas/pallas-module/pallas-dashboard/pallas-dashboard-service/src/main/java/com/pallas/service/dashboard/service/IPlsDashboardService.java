package com.pallas.service.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pallas.service.dashboard.bean.PlsDashboard;
import com.pallas.service.dashboard.bean.PlsDashboardTemplate;
import com.pallas.service.dashboard.bo.PlsDashboardBO;

import java.util.List;

/**
 * @author: jax
 * @time: 2020/12/15 16:27
 * @desc:
 */
public interface IPlsDashboardService extends IService<PlsDashboard> {

    /**
     * 获取dashboard
     *
     * @param id
     * @return
     */
    PlsDashboardBO getDashboard(Long id);

    /**
     * 获取dashboard
     *
     * @param label
     * @return
     */
    List<PlsDashboardBO> getDashboards(String label);

    /**
     * 新建并保存成模板
     *
     * @param dashboard
     * @param template
     */
    void save(PlsDashboard dashboard, PlsDashboardTemplate template);
}
