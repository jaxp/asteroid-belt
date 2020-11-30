package com.pallas.server.common.config;

import com.pallas.base.api.constant.PlsConstant;
import com.pallas.server.common.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/8/14 10:56
 * @desc: web配置
 */
@Configuration
public class Webconfig {

    @Bean
    public HttpMessageConverter httpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(new JsonMapper()) {
            @Override
            protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
                HttpServletResponse response = null;
                if (Objects.nonNull(RequestContextHolder.getRequestAttributes())) {
                    response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                }
                if (Objects.nonNull(response)) {
                    response.setHeader(PlsConstant.RESULT_TYPE_HEADER, object.getClass().getName());
                }
                super.writeInternal(object, type, outputMessage);
            }
        };
    }

}
