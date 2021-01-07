package com.pallas.service.dashboard.api.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: jax
 * @time: 2020/12/11 10:45
 * @desc:
 */
@Getter
@AllArgsConstructor
public enum Type {

    ECHARTS(0, "echarts图表"),
    CANVAS(1, "自定义图表");

    @EnumValue
    @JsonValue
    private Integer value;
    private String desc;
}
