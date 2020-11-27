package com.pallas.service.file.bo;

import com.pallas.common.utils.SpringUtils;
import com.pallas.service.file.bean.PlsFileBelonging;
import com.pallas.service.file.enums.Sensitive;
import com.pallas.service.file.service.IPlsFileBelongingService;
import com.pallas.service.file.service.IPlsFileInfoService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/11/26 14:02
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsFileInfoBO {

    private Long id;
    private String originName;
    private String extension;
    private Long fileSize;
    private String md5;
    private String module;
    private String path;
    private Sensitive sensitive;
    private Date addTime;
    private Long addUser;
    private Integer restTimes;
    private Date expireTime;

    private List<PlsFileBelonging> belongings;

    private IPlsFileInfoService fileInfoService;
    private IPlsFileBelongingService fileBelongingService;

    public PlsFileInfoBO init() {
        if (Objects.nonNull(this.id)) {
            this.fileInfoService = SpringUtils.getBean("plsFileInfoService", IPlsFileInfoService.class);
            this.fileBelongingService = SpringUtils.getBean("plsFileBelongingService", IPlsFileBelongingService.class);
        }
        return this;
    }

}
