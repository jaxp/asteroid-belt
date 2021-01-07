package com.pallas.service.dashboard.config;

import com.pallas.common.utils.SpringUtils;
import com.pallas.service.dashboard.service.IPlsDashboardDataService;
import com.pallas.service.dashboard.service.IPlsDashboardTemplateService;

import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/12/16 11:26
 * @desc:
 */
public class DashboardDataManager {

    private IPlsDashboardTemplateService templateService;
    private IPlsDashboardDataService dataService;

    public DashboardDataManager() {
        if (Objects.isNull(this.templateService)) {
            this.templateService = SpringUtils.getBean("plsDashboardTemplateService", IPlsDashboardTemplateService.class);
            this.dataService = SpringUtils.getBean("plsDashboardDataService", IPlsDashboardDataService.class);
        }
    }

    protected IPlsDashboardTemplateService templateService() {
        return this.templateService;
    }

    protected IPlsDashboardDataService dataService() {
        return this.dataService;
    }

}
