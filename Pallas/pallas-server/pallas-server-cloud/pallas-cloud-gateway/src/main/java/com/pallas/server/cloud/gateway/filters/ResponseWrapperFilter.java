package com.pallas.server.cloud.gateway.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.response.PlsResult;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/8/24 16:55
 * @desc:
 */
@Slf4j
@Component
public class ResponseWrapperFilter implements GlobalFilter, Ordered {

    private static final String TEXT_CONTENT_TYPE = "text";
    private static final String JSON_CONTENT_TYPE = "json";

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
                    MediaType contentType = originalResponse.getHeaders().getContentType();
                    String contentTypeValue = contentType.toString();
                    if (!contentTypeValue.contains(TEXT_CONTENT_TYPE) && !contentTypeValue.contains(JSON_CONTENT_TYPE)) {
                        return super.writeWith(body);
                    } else if (body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            StringBuilder sb = new StringBuilder();
                            dataBuffers.forEach(dataBuffer -> {
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);
                                sb.append(new String(content, Charsets.UTF_8));
                            });
                            String result = sb.toString();
                            byte[] resultContent = null;
                            try {
                                if (originalResponse.getStatusCode().equals(HttpStatus.OK)) {
                                    if (contentType.isCompatibleWith(MediaType.TEXT_PLAIN)) {
                                        result = jsonMapper.writeValueAsString(PlsResult.success(result));
                                        originalResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                                    } else {
                                        String className = originalResponse.getHeaders().getFirst(PlsConstant.RESULT_TYPE_HEADER);
                                        if (!Objects.equals(PlsResult.class.getName(), className)) {
                                            result = new StringBuilder("{\"code\":200,\"msg\":\"成功\",\"data\":").append(result).append("}").toString();
                                        }
                                        originalResponse.getHeaders().remove(PlsConstant.RESULT_TYPE_HEADER);
                                    }
                                } else {
                                    PlsResult plsResult = new PlsResult();
                                    plsResult.setCode(originalResponse.getRawStatusCode());
                                    result = jsonMapper.writeValueAsString(plsResult);
                                }
                            } catch (JsonProcessingException e) {
                                log.error("转换错误", e);
                            }
                            resultContent = result.getBytes(Charsets.UTF_8);
                            originalResponse.getHeaders().setContentLength(resultContent.length);
                            return bufferFactory.wrap(resultContent);
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
