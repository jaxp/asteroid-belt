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
    private BaseConverter<BO, VO> bo2voConverter;

    {
        Type[] typeArr = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        do2boConverter = new BaseConverter<>(typeArr[0], typeArr[1]);
        bo2dtoConverter = new BaseConverter<>(typeArr[1], typeArr[2]);
        bo2voConverter = new BaseConverter<>(typeArr[1], typeArr[3]);
    }

    public BO do2bo(DO d) {
        return do2boConverter.forward(d);
    }

    public DO bo2do(BO bo) {
        return do2boConverter.reverse(bo);
    }

    public DTO bo2dto(BO bo) {
        return bo2dtoConverter.forward(bo);
    }

    public BO dto2bo(DTO dto) {
        return bo2dtoConverter.reverse(dto);
    }

    public VO bo2vo(BO bo) {
        return bo2voConverter.forward(bo);
    }

    public List<BO> do2bo(List<DO> dos) {
        return dos.stream()
            .map(e -> do2bo(e))
            .collect(Collectors.toList());
    }

    public List<DO> bo2do(List<BO> bos) {
        return bos.stream()
            .map(e -> bo2do(e))
            .collect(Collectors.toList());
    }

    public List<DTO> bo2dto(List<BO> bos) {
        return bos.stream()
            .map(e -> bo2dto(e))
            .collect(Collectors.toList());
    }

    public List<BO> dto2bo(List<DTO> dtos) {
        return dtos.stream()
            .map(e -> dto2bo(e))
            .collect(Collectors.toList());
    }

    public List<VO> bo2vo(List<BO> bos) {
        return bos.stream()
            .map(e -> bo2vo(e))
            .collect(Collectors.toList());
    }

    public DTO do2dto(DO d) {
        return bo2dtoConverter.forward(do2boConverter.forward(d));
    }

    public List<DTO> do2dto(List<DO> dos) {
        return dos.stream()
            .map(e -> this.do2dto(e))
            .collect(Collectors.toList());
    }

}
