package com.pallas.service.user.controller;

import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.PlsResult;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.bo.PlsMenuBO;
import com.pallas.service.user.constant.Permission;
import com.pallas.service.user.converter.PlsMenuConverter;
import com.pallas.service.user.param.MenuModifyParam;
import com.pallas.service.user.service.IAuthService;
import com.pallas.service.user.service.IPlsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/10/27 9:21
 * @desc:
 */
@RestController
@RequestMapping("/api/user/menu")
public class MenuController {

    @Autowired
    private IAuthService authService;
    @Autowired
    private IPlsMenuService plsMenuService;
    @Autowired
    private PlsMenuConverter plsMenuConverter;

    @GetMapping("/getMenus")
    public PlsResult getMenus() {
        List<PlsMenuBO> menus = authService.getUser().getMenus();
        return PlsResult.success(plsMenuConverter.bo2vo(menus));
    }

    @PostMapping("/enableMenu")
    public PlsResult enableMenu(@RequestBody MenuModifyParam menuModifyParam) {
        if (Objects.isNull(menuModifyParam.getEnabled())) {
            throw PlsException.paramMissing("是否可用");
        }
        if (Objects.isNull(menuModifyParam.getId())) {
            throw PlsException.paramMissing("菜单编号");
        }
        int permission = authService.getUser()
            .getPermission(menuModifyParam.getId());
        if (!Permission.canEdit(permission)) {
            throw new PlsException(ResultType.PARAM_UNAUTHORIZED);
        }
        plsMenuService.update()
            .set("enabled", menuModifyParam.getEnabled())
            .eq("id", menuModifyParam.getId())
            .update();
        return PlsResult.success();
    }

}
