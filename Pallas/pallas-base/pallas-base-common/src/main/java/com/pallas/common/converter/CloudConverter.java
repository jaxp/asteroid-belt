package com.pallas.common.converter;

import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/12/16 14:43
 * @desc:
 */
public class CloudConverter<DO, BO, DTO, VO> extends CommonConverter<DO, BO, VO> {

    private BaseConverter<BO, DTO> bo2dtoConverter;

    {
        Type[] typeArr = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        bo2dtoConverter = new BaseConverter<>(typeArr[1], typeArr[2]);
    }

    public DTO bo2dto(BO bo) {
        return bo2dtoConverter.forward(bo);
    }

    public BO dto2bo(DTO dto) {
        return bo2dtoConverter.reverse(dto);
    }

    public List<DTO> bo2dto(List<BO> bos) {
        if (CollectionUtils.isEmpty(bos)) {
            return new ArrayList<>();
        }
        return bos.stream()
            .map(e -> bo2dto(e))
            .collect(Collectors.toList());
    }

    public List<BO> dto2bo(List<DTO> dtos) {
        if (CollectionUtils.isEmpty(dtos)) {
            return new ArrayList<>();
        }
        return dtos.stream()
            .map(e -> dto2bo(e))
            .collect(Collectors.toList());
    }

    public DTO do2dto(DO d) {
        return bo2dtoConverter.forward(do2boConverter.forward(d));
    }

    public List<DTO> do2dto(List<DO> dos) {
        if (CollectionUtils.isEmpty(dos)) {
            return new ArrayList<>();
        }
        return dos.stream()
            .map(e -> this.do2dto(e))
            .collect(Collectors.toList());
    }

}
