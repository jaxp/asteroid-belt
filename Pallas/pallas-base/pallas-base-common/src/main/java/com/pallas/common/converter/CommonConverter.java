package com.pallas.common.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/8/24 14:15
 * @desc:
 */
public class CommonConverter<DO, BO, DTO, VO> {

    private BaseConverter<DO, BO> do2boConverter;
    private BaseConverter<BO, DTO> bo2dtoConverter;
    private BaseConverter<DTO, VO> dto2voConverter;

    {
        Type[] typeArr = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        do2boConverter = new BaseConverter<>(typeArr[0], typeArr[1]);
        bo2dtoConverter = new BaseConverter<>(typeArr[1], typeArr[2]);
        dto2voConverter = new BaseConverter<>(typeArr[2], typeArr[3]);
    }

    public BO do2bo(DO d) {
        return do2boConverter.forward(d);
    }

    public DTO bo2dto(BO b) {
        return bo2dtoConverter.forward(b);
    }

    public VO dto2vo(DTO dto) {
        return dto2voConverter.forward(dto);
    }

    public BO dto2bo(DTO dto) {
        return bo2dtoConverter.reverse(dto);
    }

    public List<BO> do2bo(List<DO> d) {
        return d.stream()
            .map(e -> this.do2bo(e))
            .collect(Collectors.toList());
    }

    public List<DTO> bo2dto(List<BO> b) {
        return b.stream()
            .map(e -> this.bo2dto(e))
            .collect(Collectors.toList());
    }

    public List<VO> dto2vo(List<DTO> dto) {
        return dto.stream()
            .map(e -> this.dto2vo(e))
            .collect(Collectors.toList());
    }

    public List<BO> dto2bo(List<DTO> dto) {
        return dto.stream()
            .map(e -> this.dto2bo(e))
            .collect(Collectors.toList());
    }

    public DTO do2dto(DO d) {
        return bo2dtoConverter.forward(do2boConverter.forward(d));
    }

    public List<DTO> do2dto(List<DO> d) {
        return d.stream()
            .map(e -> this.do2dto(e))
            .collect(Collectors.toList());
    }

}
