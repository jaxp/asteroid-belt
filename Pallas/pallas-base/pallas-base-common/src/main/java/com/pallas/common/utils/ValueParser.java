package com.pallas.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: jax
 * @time: 2020/9/28 10:14
 * @desc:
 */
@Data
@AllArgsConstructor
public class ValueParser {
    private Object value;
    private Class type;
}
