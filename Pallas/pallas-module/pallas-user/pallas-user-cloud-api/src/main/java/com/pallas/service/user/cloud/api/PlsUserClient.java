package com.pallas.service.user.cloud.api;

import com.pallas.service.user.api.IPlsUserApi;
import com.pallas.service.user.dto.PlsUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: jax
 * @time: 2020/9/27 16:35
 * @desc:
 */
@FeignClient(name = "pallas-cloud-user", contextId = "user")
public interface PlsUserClient extends IPlsUserApi {

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/api/cloud/user/getCurrent")
    PlsUserDTO getCurrent();

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/api/cloud/user/getUser")
    PlsUserDTO getUser(@RequestParam String username);
}
