package com.pallas.service.file.dto;

import com.pallas.service.file.enums.OrganizationType;
import com.pallas.service.file.enums.Sensitive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/11/26 10:01
 * @desc: 文件归属
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsFileUpload {
    private String module;
    private String fileName;
    private Long fileSize;
    private byte[] content;
    private Integer restTimes;
    private Date expireTime;
    private Sensitive sensitive;
    private List<PlsFileBelongingDTO> belongings;

    public PlsFileUpload authorized() {
        return setSensitive(Sensitive.AUTHORIZED);
    }

    public PlsFileUpload belongTo(OrganizationType organizationType, List<Long> organizations) {
        if (CollectionUtils.isNotEmpty(organizations)) {
            this.setSensitive(Sensitive.ORGANIZATION);
            List<PlsFileBelongingDTO> belongings = organizations.stream()
                .map(e -> new PlsFileBelongingDTO(organizationType, e))
                .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(this.belongings)) {
                this.belongings = belongings;
            } else {
                this.belongings.addAll(belongings);
            }
        }
        return this;
    }

}
