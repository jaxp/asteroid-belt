package com.pallas.service.user.cloud.controller;

import com.pallas.service.user.api.IPlsAuthorityApi;
import com.pallas.service.user.service.IPlsAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * @author: jax
 * @time: 2020/12/17 17:08
 * @desc:
 */
@RestController
@RequestMapping("/api/cloud/auth")
public class AuthorityCloudController implements IPlsAuthorityApi {

    @Autowired
    private IPlsAuthorityService plsAuthorityService;

    @Override
    @GetMapping("/getAuthority")
    public Integer getAuthority(@RequestParam String resourceType, @RequestParam Long resource) {
        return plsAuthorityService.getAuthority(resourceType, resource);
    }

    @Override
    @PostMapping("/getAuthorityMap")
    public Map<Long, Integer> getAuthorityMap(@RequestBody Set<Long> resources) {
        return plsAuthorityService.getAuthorityMap(resources);
    }
}
