package com.teligen.demo.utils;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EntityUtils {

    private static Logger logger = Logger.getLogger(EntityUtils.class);

    public static <T1, T2> T2 coptData(T1 sourceObj, Class<T2> clazz){
        T2 targetObj = getInstance(clazz);
        return copyFieldsValue(sourceObj, targetObj);
    }

    public static <T1, T2> T2 copyFieldsValue(T1 sourceObj, T2 targetObj){
        Class sourceClazz = sourceObj.getClass();
        Class targetClazz = targetObj.getClass();
        Field[] sourceFields = sourceClazz.getFields();
        Field[] targetFields = targetClazz.getFields();
        for(Field sourceField : sourceFields){
            for(Field targetField : targetFields){
                if(sourceField.getName().equals(targetField.getName())
                        && equalFieldsType(sourceField, targetField)){
                    copyFieldValue(sourceObj, targetObj, sourceField, targetField);
                    break;
                }
            }
        }
        return targetObj;
    }

    public static <T1, T2> void copyFieldValue(T1 sourceObj, T2 targetObj, Field sourceField, Field targetField){
        try {
            sourceField.setAccessible(true);
            Object sourceValue = sourceField.get(sourceObj);
            targetField.setAccessible(true);
            targetField.set(targetObj, sourceValue);
            targetField.setAccessible(false);
            sourceField.setAccessible(false);
        } catch (IllegalAccessException e) {
            logger.error("--------------copyFieldValue字段值拷贝异常");
            logger.error(e.getMessage());
        }
    }

    /**
     * 判断两个字段的类型是否相同
     *
     * @param field1 复制源
     * @param field2 复制目标
     * @return
     */
    public static boolean equalFieldsType(Field field1, Field field2) {
        String fTypeName1 = field1.getType().getSimpleName();
        String fTypeName2 = field2.getType().getSimpleName();
        //1. 处理基本数据类型和包装类
        Map<String, String> map = new HashMap<String, String>();
        map.put(int.class.getSimpleName(), Integer.class.getSimpleName());
        map.put(byte.class.getSimpleName(), Byte.class.getSimpleName());
        map.put(short.class.getSimpleName(), Short.class.getSimpleName());
        map.put(char.class.getSimpleName(), Character.class.getSimpleName());
        map.put(long.class.getSimpleName(), Long.class.getSimpleName());
        map.put(float.class.getSimpleName(), Float.class.getSimpleName());
        map.put(double.class.getSimpleName(), Double.class.getSimpleName());
        map.put(boolean.class.getSimpleName(), Boolean.class.getSimpleName());

        /**
         * 在涉及包装类的判断逻辑中，源数据不能是包装类
         * 因为包装类一旦为null，会引发异常
         */
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if (key.equals(fTypeName1) && map.get(key).equals(fTypeName2)) {
                return true;
            }
        }
        //2. 名称相同、类型相同
        if (fTypeName1.equals(fTypeName2)) {
            return true;
        }
        return false;
    }

    public static <T> T getInstance(Class<T> clazz){
        try{
            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            logger.error("------------EntityUtils实例化目标对象异常");
            logger.error(e.getMessage());
        } catch (InstantiationException e) {
            logger.error("------------EntityUtils实例化目标对象异常");
            logger.error(e.getMessage());
        }
        return null;
    }

    public static <T1, T2> void copyData(T1 soureObj, T2 targetObj){

    }
}
