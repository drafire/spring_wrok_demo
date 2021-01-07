package com.teligen.demo.utils;

import com.teligen.demo.dto.ResultCode;
import com.teligen.demo.exception.BusinessException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author
 * @description 枚举工具类
 * @date 2020-06-08
 * 必须要有getCode方法
 */
public class EnumUtils {
    /**
     * 判断数值是否属于枚举类的值
     *
     * @param clzz 枚举类 Enum
     * @param code
     * @return
     * @author
     */
    public static boolean isInclude(Class<?> clzz, Object code)
            throws InvocationTargetException,
            IllegalAccessException, NoSuchMethodException {

        return isInclude(clzz, code, "getCode");
    }

    /**
     * 判断数值是否属于枚举类的值
     *
     * @param clzz 枚举类 Enum
     * @param code
     * @return
     * @author
     */
    public static boolean isInclude(Class<?> clzz, Object code, String method)
            throws InvocationTargetException,
            IllegalAccessException, NoSuchMethodException {

        boolean include = false;
        if (clzz.isEnum()) {
            Object[] enumConstants = clzz.getEnumConstants();
            Method getCode = clzz.getMethod(method);
            for (Object enumConstant : enumConstants) {
                if (getCode.invoke(enumConstant).equals(code)) {
                    include = true;
                    break;
                }
            }
        }
        return include;
    }

    /**
     * @param clzz 枚举类 Enum
     * @param code code
     * @return 枚举类型父类
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Enum getEnum(Class<?> clzz, Object code) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return getEnum(clzz, code, "getCode");
    }

    /**
     * @param clzz 枚举类 Enum
     * @param code code
     * @return 枚举类型父类
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Enum getEnum(Class<?> clzz, Object code, String method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (clzz.isEnum()) {
            Object[] enumConstants = clzz.getEnumConstants();
            Method getCode = clzz.getMethod(method);
            for (Object enumConstant : enumConstants) {
                if (getCode.invoke(enumConstant).equals(code)) {
                    return (Enum) enumConstant;
                }
            }
            throw new BusinessException(ResultCode.ENUM_CODE_NOT_EXIST.getCode(), ResultCode.ENUM_CODE_NOT_EXIST.getDes());
        }

        throw new BusinessException(ResultCode.NOT_ENUM.getCode(), ResultCode.NOT_ENUM.getDes());
    }
}