package com.pallas.service.user.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jax
 * @time: 2020/11/17 14:14
 * @desc:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuModifyParam {
    private Long id;
    private Boolean enabled;
}
