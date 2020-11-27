package com.pallas.service.file.dto;

import com.pallas.service.file.enums.OrganizationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: jax
 * @time: 2020/11/26 13:24
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsFileBelongingDTO {

    /**
     * 组织
     */
    private OrganizationType organizationType;
    /**
     * 组织编号
     */
    private Long organization;

}
