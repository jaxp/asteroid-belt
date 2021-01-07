package com.pallas.service.dashboard.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.dashboard.bean.PlsDashboard;
import com.pallas.service.dashboard.bean.PlsDashboardTemplate;
import com.pallas.service.dashboard.bo.PlsDashboardBO;
import com.pallas.service.dashboard.bo.PlsDashboardTemplateBO;
import com.pallas.service.dashboard.converter.DashboardConverter;
import com.pallas.service.dashboard.mapper.PlsDashboardMapper;
import com.pallas.service.dashboard.service.IPlsDashboardService;
import com.pallas.service.dashboard.service.IPlsDashboardTemplateService;
import com.pallas.service.user.api.IPlsAuthorityApi;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/12/15 16:29
 * @desc:
 */
@Service
public class PlsDashboardService extends ServiceImpl<PlsDashboardMapper, PlsDashboard> implements IPlsDashboardService {

    @Autowired
    private DashboardConverter dashboardConverter;
    @Autowired
    private IPlsDashboardTemplateService plsDashboardTemplateService;
    @Autowired
    private IPlsAuthorityApi authorityApi;

    @Override
    public PlsDashboardBO getDashboard(Long id) {
        PlsDashboard dashboard = getById(id);
        if (Objects.nonNull(dashboard)) {
            return dashboardConverter.do2bo(dashboard);
        }
        return null;
    }

    @Override
    public List<PlsDashboardBO> getDashboards(String label) {
        List<PlsDashboard> dashboards = query()
            .eq("label", label)
            .orderByAsc("sort")
            .list();
        if (CollectionUtils.isNotEmpty(dashboards)) {
            List<PlsDashboardBO> dashboardBOs = dashboardConverter.do2bo(dashboards);
            // 填充模板
            Set<Long> templateIds = dashboardBOs.stream()
                .map(PlsDashboardBO::getTemplateId)
                .collect(Collectors.toSet());
            List<PlsDashboardTemplateBO> templateBOs = plsDashboardTemplateService.getTemplates(templateIds);
            Map<Long, PlsDashboardTemplateBO> templateMap = templateBOs.stream()
                .collect(Collectors.toMap(PlsDashboardTemplateBO::getId, e -> e));
            dashboardBOs.forEach(e -> e.setTemplate(templateMap.get(e.getTemplateId())));
            // 获取权限
            Set<Long> resources = dashboardBOs.stream()
                .map(PlsDashboardBO::getId)
                .collect(Collectors.toSet());
            Map<Long, Integer> permissionMap = authorityApi.getAuthorityMap(resources);
            dashboardBOs.forEach(e -> e.setPermission(permissionMap.get(e.getId())));
            return dashboardBOs;
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public void save(PlsDashboard dashboard, PlsDashboardTemplate template) {
        plsDashboardTemplateService.save(template);
        dashboard.setTemplateId(template.getId());
        save(dashboard);
    }

}
