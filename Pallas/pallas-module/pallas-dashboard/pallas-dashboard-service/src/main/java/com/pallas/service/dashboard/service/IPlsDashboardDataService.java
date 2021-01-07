package com.pallas.service.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pallas.service.dashboard.bean.PlsDashboardData;
import com.pallas.service.dashboard.bo.PlsDashboardDataBO;

import java.util.List;
import java.util.Set;

/**
 * @author: jax
 * @time: 2020/12/15 16:28
 * @desc:
 */
public interface IPlsDashboardDataService extends IService<PlsDashboardData> {
    /**
     * 获取模板下数据配置
     *
     * @param id
     * @return
     */
    List<PlsDashboardDataBO> getData(Long id);

    /**
     * 获取模板下数据配置
     *
     * @param id
     * @return
     */
    List<PlsDashboardDataBO> getData(Set<Long> ids);
}
