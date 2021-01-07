package com.pallas.service.dashboard.controller;

import com.google.common.base.Strings;
import com.pallas.base.api.response.PlsResult;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.dashboard.api.contant.DashboardConstant;
import com.pallas.service.dashboard.api.enums.Type;
import com.pallas.service.dashboard.bean.PlsDashboard;
import com.pallas.service.dashboard.bean.PlsDashboardTemplate;
import com.pallas.service.dashboard.bo.PlsDashboardBO;
import com.pallas.service.dashboard.converter.DashboardConverter;
import com.pallas.service.dashboard.param.DashboardParams;
import com.pallas.service.dashboard.service.IPlsDashboardService;
import com.pallas.service.user.api.IPlsAuthorityApi;
import com.pallas.service.user.api.IPlsUserApi;
import com.pallas.service.user.constant.Permission;
import com.pallas.service.user.constant.ResourceType;
import com.pallas.service.user.dto.PlsUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: jax
 * @time: 2020/12/11 11:18
 * @desc:
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private IPlsDashboardService plsDashboardService;
    @Autowired
    private DashboardConverter dashboardConverter;
    @Autowired
    private IPlsAuthorityApi authorityApi;
    @Autowired
    private IPlsUserApi userApi;

    @GetMapping("/hasDevAuth")
    public PlsResult hasDevAuth() {
        Integer permission = authorityApi.getAuthority(ResourceType.DASHBOARD, DashboardConstant.DEV_RESOURCE);
        return PlsResult.success(permission);
    }

    @GetMapping("/{label}")
    public PlsResult getDashboards(@PathVariable String label) {
        List<PlsDashboardBO> dashboards = plsDashboardService.getDashboards(label);
        return PlsResult.success(dashboardConverter.bo2vo(dashboards));
    }

    @PostMapping("/save")
    public PlsResult save(@RequestBody DashboardParams params) {
        Integer permission = authorityApi.getAuthority(ResourceType.DASHBOARD, DashboardConstant.DEV_RESOURCE);
        if (Permission.forbidden(permission)) {
            return PlsResult.error(ResultType.PARAM_UNAUTHORIZED);
        }
        PlsUserDTO user = userApi.getCurrent();
        PlsDashboard dashboard = new PlsDashboard()
            .setLabel(params.getLabel())
            .setName(params.getName())
            .setSort(params.getSort())
            .setHeight(params.getHeight())
            .setWidth(params.getWidth())
            .setType(Type.ECHARTS)
            .setAddUser(user.getId());
        if (Strings.isNullOrEmpty(params.getTemplateName())) {
            dashboard.setContent(params.getContent());
            plsDashboardService.save(dashboard);
        } else {
            PlsDashboardTemplate template = new PlsDashboardTemplate()
                .setName(params.getTemplateName())
                .setRemark(params.getTemplateRemark())
                .setType(Type.ECHARTS)
                .setContent(params.getContent())
                .setAddUser(user.getId());
            plsDashboardService.save(dashboard, template);
        }
        return PlsResult.success();
    }

}
