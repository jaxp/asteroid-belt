package com.pallas.service.user.constant;

import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/12/10 9:38
 * @desc:
 */
public final class Permission {
    /**
     * 无权限
     */
    public static final int NONE = 0;
    /**
     * 全部权限
     */
    public static final int ALL = 0b1111;
    /**
     * 查询
     */
    public static final int QUERY = 0b0001;
    /**
     * 新增
     */
    public static final int INSERT = 0b1001;
    /**
     * 修改
     */
    public static final int EDIT = 0b0011;
    /**
     * 删除
     */
    public static final int DELETE = 0b0101;
    /**
     * 新增编辑
     */
    public static final int INSERT_EDIT = 0b1011;
    /**
     * 新增删除
     */
    public static final int INSERT_DELETE = 0b1101;
    /**
     * 编辑删除
     */
    public static final int EDIT_DELETE = 0b0111;

    public static boolean forbidden(int permission) {
        return Objects.equals(permission, NONE);
    }
    public static boolean canInsert(int permission) {
        return (0b1000 & permission) > 0;
    }
    public static boolean canDelete(int permission) {
        return (0b0100 & permission) > 0;
    }
    public static boolean canEdit(int permission) {
        return (0b0010 & permission) > 0;
    }
    public static boolean canView(int permission) {
        return (0b0001 & permission) > 0;
    }

}
