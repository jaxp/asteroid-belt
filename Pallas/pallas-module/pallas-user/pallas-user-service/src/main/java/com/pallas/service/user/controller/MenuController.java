package com.pallas.service.user.controller;

import com.pallas.base.api.response.PlsResult;
import com.pallas.service.user.converter.PlsMenuConverter;
import com.pallas.service.user.service.IPlsMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jax
 * @time: 2020/10/27 9:21
 * @desc:
 */
@RestController
@RequestMapping("/api/user/menu")
public class MenuController {

    @Autowired
    private IPlsMenuService plsMenuService;
    @Autowired
    private PlsMenuConverter plsMenuConverter;

    @GetMapping("/getMenus")
    public PlsResult getMenus() {
        return PlsResult.success(plsMenuConverter.dto2vo(plsMenuService.getUserMenus()));
    }

}
