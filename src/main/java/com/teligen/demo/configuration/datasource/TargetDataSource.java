package com.teligen.demo.configuration.datasource;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @desc 目标数据源注解，注解在方法上指定数据源的名称
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Component
@Documented
public @interface TargetDataSource {

    String value(); //此处接收的是数据源的名称

}