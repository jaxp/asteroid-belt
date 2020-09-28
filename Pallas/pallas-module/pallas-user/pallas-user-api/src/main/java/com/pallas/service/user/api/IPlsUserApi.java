package com.pallas.service.user.api;

import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.constant.UserConstant;
import com.pallas.service.user.dto.PlsUserDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: jax
 * @time: 2020/8/14 14:17
 * @desc: 用户服务接口
 */
public interface IPlsUserApi {

    default long getUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userId = request.getHeader(UserConstant.USER_ID_HEADER);
        if (StringUtils.isEmpty(userId)) {
            throw new PlsException(ResultType.AUTHORIZATION_EXCEPTION);
        }
        return Long.parseLong(userId);
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    PlsUserDTO getCurrent();

    /**
     * 获取用户
     *
     * @param username
     * @return
     */
    PlsUserDTO getUser(String username);

}
