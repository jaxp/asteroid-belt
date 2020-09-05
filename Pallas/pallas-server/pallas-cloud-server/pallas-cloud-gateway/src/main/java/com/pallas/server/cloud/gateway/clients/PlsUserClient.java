package com.pallas.server.cloud.gateway.clients;

import com.pallas.service.user.api.IPlsAuthApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: jax
 * @time: 2020/9/4 16:50
 * @desc:
 */
@FeignClient("pallas-cloud-user")
public interface PlsUserClient extends IPlsAuthApi {

  @Override
  @RequestMapping(method = RequestMethod.GET, value = "/getUser")
  Long getUser(String token);
}
