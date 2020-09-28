package com.pallas.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author: jax
 * @time: 2020/9/28 9:18
 * @desc:
 */
@Slf4j
public class PlsMapUtils {

    public static final void copyProperties(Map<String, ?> origin, Object target) {
        copyProperties(origin, target, null);
    }
    public static final void copyProperties(Map<String, ?> origin, Object target, Function<ValueParser, Object> func) {
        Field[] fields = target.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (origin.containsKey(fieldName)) {
                boolean accessible = field.isAccessible();
                if (!accessible) {
                    field.setAccessible(true);
                }
                Object value = origin.get(fieldName);
                Class type = field.getType();
                if (Objects.nonNull(func)) {
                    value = func.apply(new ValueParser(value, type));
                }
                try {
                    field.set(target, value);
                } catch (Exception e) {
                    log.warn("参数拷贝错误【{}:{}】", fieldName, value);
                }
                if (!accessible) {
                    field.setAccessible(false);
                }
            }
        }
    }

}
