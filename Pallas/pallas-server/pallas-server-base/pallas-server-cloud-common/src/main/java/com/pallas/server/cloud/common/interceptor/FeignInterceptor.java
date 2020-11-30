package com.pallas.server.cloud.common.interceptor;


import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.server.common.json.JsonMapper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/11/26 16:01
 * @desc:
 */
public class FeignInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (Objects.equals(response.header(PlsConstant.RESULT_TYPE_HEADER), PlsException.class.getName())) {
            throw new JsonMapper().readValue(response.body().string(), PlsException.class);
        }
        return response;
    }
}
