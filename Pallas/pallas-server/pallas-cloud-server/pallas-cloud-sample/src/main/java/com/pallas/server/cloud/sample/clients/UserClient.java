package com.pallas.server.cloud.sample.clients;

import com.pallas.service.user.api.IUserApi;
import com.pallas.service.user.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: jax
 * @time: 2020/8/14 13:35
 * @desc: userFeignClient
 */
@FeignClient("pallas-cloud-user")
public interface UserClient extends IUserApi {

  @Override
  @RequestMapping(method = RequestMethod.GET, value = "/user")
  UserDTO getUser();

}
