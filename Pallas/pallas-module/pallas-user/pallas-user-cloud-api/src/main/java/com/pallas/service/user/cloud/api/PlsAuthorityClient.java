package com.pallas.service.user.cloud.api;

import com.pallas.service.user.api.IPlsAuthorityApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * @author: jax
 * @time: 2020/12/17 17:07
 * @desc:
 */
@FeignClient(name = "pallas-cloud-user", contextId = "authority")
public interface PlsAuthorityClient extends IPlsAuthorityApi {

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/api/cloud/auth/getAuthority")
    Integer getAuthority(@RequestParam String resourceType, @RequestParam Long resource);

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/api/cloud/auth/getAuthorityMap")
    Map<Long, Integer> getAuthorityMap(Set<Long> resources);
}
