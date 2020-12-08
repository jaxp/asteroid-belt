package com.pallas.service.file.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: jax
 * @time: 2020/12/8 19:08
 * @desc: 上传状态
 */
@Getter
@AllArgsConstructor
public enum FileStatus {

    PREPARED(0, "准备阶段"),
    SUCCESS(1, "成功"),
    FAIL(2, "失败");

    @JsonValue
    @EnumValue
    private Integer value;
    private String desc;
}
