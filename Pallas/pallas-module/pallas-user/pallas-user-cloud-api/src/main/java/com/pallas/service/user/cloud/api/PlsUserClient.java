package com.pallas.service.user.cloud.api;

import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.api.IPlsUserApi;
import com.pallas.service.user.constant.UserConstant;
import com.pallas.service.user.dto.PlsUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: jax
 * @time: 2020/9/27 16:35
 * @desc:
 */
@FeignClient("pallas-cloud-user")
public interface PlsUserClient extends IPlsUserApi {

    default long getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = request.getHeader(UserConstant.USER_ID_HEADER);
        if (StringUtils.isEmpty(userId)) {
            throw new PlsException(ResultType.AUTHORIZATION_ERR);
        }
        return Long.parseLong(userId);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/api/user/getCurrent")
    PlsUserDTO getCurrent(@RequestParam Long userId);

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/user")
    PlsUserDTO getUser(@RequestParam String username);
}
