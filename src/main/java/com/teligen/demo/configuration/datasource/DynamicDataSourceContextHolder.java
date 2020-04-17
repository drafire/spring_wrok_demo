package com.teligen.demo.configuration.datasource;

public class DynamicDataSourceContextHolder {
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源
     * ***/
    public static void setDataSource(String db){
        contextHolder.set(db);
    }

    /**
     * 获取当前数据源
     * ***/
    public static String getDataSource(){
        return contextHolder.get();
    }

    /***
     * 清楚上下文数据
     * ***/
    public static void clear(){
        contextHolder.remove();
    }
}
