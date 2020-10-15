package com.pallas.server.common.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializerBase;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author: jax
 * @time: 2020/8/14 10:56
 * @desc: 统一序列化
 */
public class JsonMapper extends ObjectMapper {

    public JsonMapper() {
        this(JsonInclude.Include.ALWAYS);
    }

    private JsonMapper(JsonInclude.Include include) {
        // 设置输出时包含属性的风格
        if (include != null) {
            this.setSerializationInclusion(include);
        }
        // Json 格式定义
        this.enableSimple();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        SimpleModule simpleModule = new SimpleModule();
        // long会精度缺失，处理成字符串
        ToStringSerializerBase toStringSerializer = ToStringSerializer.instance;
        simpleModule.addSerializer(Long.class, toStringSerializer);
        simpleModule.addSerializer(Long.TYPE, toStringSerializer);
        simpleModule.addSerializer(Date.class, new DateSerializer() {
            @Override
            public void serialize(Date value, JsonGenerator g, SerializerProvider provider) throws IOException {
                if (_asTimestamp(provider)) {
                    g.writeString(_timestamp(value) + "");
                    return;
                }
                _serializeAsString(value, g, provider);
            }
        });
        this.registerModule(simpleModule);
    }

    /**
     * 不允许单引号
     * 不允许不带引号的字段名称
     * 禁止重复字段
     *
     * @return the json mapper
     */
    public JsonMapper enableSimple() {
        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, false)
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false)
            .configure(JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION, true);
        // 时区
        this.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return this;
    }
}
