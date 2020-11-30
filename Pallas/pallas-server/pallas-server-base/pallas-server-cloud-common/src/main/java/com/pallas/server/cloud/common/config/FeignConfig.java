package com.pallas.server.cloud.common.config;

import com.pallas.server.cloud.common.interceptor.FeignInterceptor;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author: jax
 * @time: 2020/11/26 16:01
 * @desc:
 */
@Configuration
public class FeignConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(10, 3, TimeUnit.MINUTES))
            .addInterceptor(new FeignInterceptor())
            .build();
    }

}
