package com.pallas.server.cloud.sample.clients;

import com.pallas.service.user.api.IPlsUserApi;
import com.pallas.service.user.dto.PlsUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: jax
 * @time: 2020/8/14 13:35
 * @desc: userFeignClient
 */
@FeignClient("pallas-cloud-user")
public interface PlsUserClient extends IPlsUserApi {

  @Override
  @RequestMapping(method = RequestMethod.GET, value = "/user")
  PlsUserDTO getUser(String username);
}
