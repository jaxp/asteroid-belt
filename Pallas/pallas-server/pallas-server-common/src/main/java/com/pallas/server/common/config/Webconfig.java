package com.pallas.server.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallas.server.common.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author: jax
 * @time: 2020/8/14 10:56
 * @desc: web配置
 */
@Configuration
public class Webconfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new JsonMapper();
    }

    @Bean
    public HttpMessageConverter httpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(this.objectMapper());
    }

}
