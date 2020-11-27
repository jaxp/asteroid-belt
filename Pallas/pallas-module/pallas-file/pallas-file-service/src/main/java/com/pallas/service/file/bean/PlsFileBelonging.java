package com.pallas.service.file.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pallas.service.user.enums.OrganizationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: jax
 * @time: 2020/11/26 13:09
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_f_belonging")
public class PlsFileBelonging {

    @TableId
    private Long id;
    /**
     * 文件编号
     */
    private Long fileId;
    /**
     * 组织
     */
    private OrganizationType organizationType;
    /**
     * 组织编号
     */
    private Long organization;
}
