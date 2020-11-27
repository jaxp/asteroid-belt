package com.pallas.base.batis.handler;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/11/27 13:47
 * @desc:
 */
@Slf4j
@Component
public class BaseMetaObjectHandler implements MetaObjectHandler {

    private Map<Class, List<String>> insertColumnMap = new HashMap<>();
    private Map<Class, List<String>> updateColumnMap = new HashMap<>();

    @Override
    public void insertFill(MetaObject metaObject) {
        List<String> columns = getInsertColumns(metaObject);
        setValue(columns, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        List<String> columns = getUpdateColumns(metaObject);
        setValue(columns, metaObject);
    }

    private List<String> getInsertColumns(MetaObject metaObject) {
        Class clazz = parseColumns(metaObject, true);
        return insertColumnMap.get(clazz);
    }

    private List<String> getUpdateColumns(MetaObject metaObject) {
        Class clazz = parseColumns(metaObject, false);
        return updateColumnMap.get(clazz);
    }

    private Class parseColumns(MetaObject metaObject, boolean insert) {
        Class clazz = null;
        if (metaObject.hasGetter(Constants.ENTITY)) {
            clazz = metaObject.getValue(Constants.ENTITY).getClass();
        } else {
            clazz = metaObject.getOriginalObject().getClass();
        }
        if (insert && insertColumnMap.containsKey(clazz) || !insert && updateColumnMap.containsKey(clazz)) {
            return clazz;
        }
        Field[] fields = clazz.getDeclaredFields();
        List<String> insertColumns = new ArrayList<>();
        List<String> updateColumns = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(TableField.class)) {
                TableField tableField = field.getAnnotation(TableField.class);
                if (Objects.equals(tableField.fill(), FieldFill.INSERT) || Objects.equals(tableField.fill(), FieldFill.INSERT_UPDATE)) {
                    insertColumns.add(field.getName());
                }
                if (Objects.equals(tableField.fill(), FieldFill.UPDATE) || Objects.equals(tableField.fill(), FieldFill.INSERT_UPDATE)) {
                    updateColumns.add(field.getName());
                }
            }
        }
        insertColumnMap.put(clazz, insertColumns);
        updateColumnMap.put(clazz, updateColumns);
        return clazz;
    }

    private void setValue(List<String> columns, MetaObject metaObject) {
        try {
            for (String column : columns) {
                if (Objects.isNull(this.getFieldValByName(column, metaObject))) {
                    this.setFieldValByName(column, new Date(), metaObject);
                }
            }
        } catch (Exception e) {
            log.error("通用设置默认值方式失败");
        }
    }
}
