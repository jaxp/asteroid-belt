package com.pallas.service.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pallas.service.dashboard.bean.PlsDashboardTemplate;
import com.pallas.service.dashboard.bo.PlsDashboardTemplateBO;

import java.util.List;
import java.util.Set;

/**
 * @author: jax
 * @time: 2020/12/11 11:10
 * @desc:
 */
public interface IPlsDashboardTemplateService extends IService<PlsDashboardTemplate> {
    /**
     * 获取图表模板
     *
     * @param id
     * @return
     */
    PlsDashboardTemplateBO getTemplate(Long id);

    /**
     * 获取图表模板
     *
     * @param ids
     * @return
     */
    List<PlsDashboardTemplateBO> getTemplates(Set<Long> ids);
}
