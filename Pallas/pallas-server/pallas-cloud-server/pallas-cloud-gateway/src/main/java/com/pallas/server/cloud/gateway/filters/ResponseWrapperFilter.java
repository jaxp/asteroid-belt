package com.pallas.server.cloud.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.response.PlsResult;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author: jax
 * @time: 2020/8/24 16:55
 * @desc:
 */
@Component
public class ResponseWrapperFilter implements GlobalFilter, Ordered {

    private static final String PLACEHOLDER = "$$";
    private static final String REPLACE_PLACEHOLDER = "\"$$\"";

    @Autowired
    private ObjectMapper jsonMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                try {
                    if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                        return super.writeWith(fluxBody.map(dataBuffer -> {
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            DataBufferUtils.release(dataBuffer);
                            String result = new String(content, Charsets.UTF_8);
                            if (originalResponse.getStatusCode().equals(HttpStatus.OK)) {
                                if (result.startsWith(PlsConstant.ERR_PREFIX)) {
                                    // 规范化的异常结果
                                    result = result.substring(PlsConstant.ERR_PREFIX.length());
                                } else {
                                    PlsResult plsResult = PlsResult.success(PLACEHOLDER);
                                    try {
                                        String generalResult = jsonMapper.writeValueAsString(plsResult);
                                        result = generalResult.replace(REPLACE_PLACEHOLDER, result);
                                    } catch (JsonProcessingException e) {
                                        return bufferFactory.wrap(content);
                                    }
                                }
                            } else {
                                PlsResult plsResult = new PlsResult();
                                plsResult.setCode(originalResponse.getRawStatusCode());
                                plsResult.setMsg(originalResponse.getStatusCode().getReasonPhrase());
                                try {
                                    result = jsonMapper.writeValueAsString(plsResult);
                                } catch (JsonProcessingException e) {
                                    return bufferFactory.wrap(content);
                                }
                            }
                            content = result.getBytes(Charsets.UTF_8);
                            originalResponse.getHeaders().setContentLength(content.length);
                            return bufferFactory.wrap(content);
                        }));
                    }
                    return super.writeWith(body);
                } catch (Exception e) {
                    return chain.filter(exchange);
                }
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
