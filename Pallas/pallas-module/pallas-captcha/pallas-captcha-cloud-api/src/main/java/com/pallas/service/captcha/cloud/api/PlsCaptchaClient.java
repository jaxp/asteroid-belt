package com.pallas.service.captcha.cloud.api;

import com.pallas.service.captcha.api.IPlsCaptchaApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: jax
 * @time: 2020/11/29 10:18
 * @desc:
 */
@FeignClient("pallas-cloud-catcha")
public interface PlsCaptchaClient extends IPlsCaptchaApi {

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/api/cloud/captcha/validate")
    boolean validate(String certificate);
}
