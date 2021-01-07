package com.pallas.common.converter;

import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/8/24 14:15
 * @desc:
 */
public class CommonConverter<DO, BO, VO> {

    protected BaseConverter<DO, BO> do2boConverter;
    protected BaseConverter<BO, VO> bo2voConverter;

    {
        Type[] typeArr = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        do2boConverter = new BaseConverter<>(typeArr[0], typeArr[1]);
        bo2voConverter = new BaseConverter<>(typeArr[1], typeArr[typeArr.length - 1]);
    }

    public BO do2bo(DO d) {
        return do2boConverter.forward(d);
    }

    public DO bo2do(BO bo) {
        return do2boConverter.reverse(bo);
    }

    public VO bo2vo(BO bo) {
        return bo2voConverter.forward(bo);
    }

    public List<BO> do2bo(List<DO> dos) {
        if (CollectionUtils.isEmpty(dos)) {
            return new ArrayList<>();
        }
        return dos.stream()
            .map(e -> do2bo(e))
            .collect(Collectors.toList());
    }

    public List<DO> bo2do(List<BO> bos) {
        if (CollectionUtils.isEmpty(bos)) {
            return new ArrayList<>();
        }
        return bos.stream()
            .map(e -> bo2do(e))
            .collect(Collectors.toList());
    }

    public List<VO> bo2vo(List<BO> bos) {
        if (CollectionUtils.isEmpty(bos)) {
            return new ArrayList<>();
        }
        return bos.stream()
            .map(e -> bo2vo(e))
            .collect(Collectors.toList());
    }

}
