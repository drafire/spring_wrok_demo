package com.teligen.demo.configuration.aspect;

import com.teligen.demo.configuration.datasource.DataSourceKey;
import com.teligen.demo.configuration.datasource.DynamicDataSourceContextHolder;
import com.teligen.demo.configuration.datasource.TargetDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @desc 数据源AOP切面定义
 */
@Aspect
@Component
public class DataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
    /**
     * 切换放在mapper接口的方法上，所以这里要配置AOP切面的切入点
     *
     * execution()是最常用的切点函数，其语法如下所示：
     *  整个表达式可以分为五个部分：
     *  1、execution(): 表达式主体。
     *  2、第一个*号：表示返回类型，*号表示所有的类型。
     *  3、包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.demo.service.impl包、子孙包下所有类的方法。
     *  4、第二个*号：表示类名，*号表示所有的类。
     *  5、*(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
     *
     */
    @Pointcut("execution(* com.teligen.demo.mapper..*.*(..))")
    public void dataSourcePointCut(){

    }

    /**
     * 根据切点信息获取调用函数是否用TargetDataSource切面注解描述，如果设置了数据源，则进行数据源切换
     */
    @Before("dataSourcePointCut()")
    public void before(JoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        String method = joinPoint.getSignature().getName();
        Class<?>[] clazz =  target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        try{
            Method m = clazz[0].getMethod(method,parameterTypes);
            if(null!=m && m.isAnnotationPresent(TargetDataSource.class)){
                TargetDataSource td = m.getAnnotation(TargetDataSource.class);
                String dataSourceName = td.value();
                DynamicDataSourceContextHolder.setDataSource(dataSourceName);
                logger.info("current thread " + Thread.currentThread().getName() + " add " + dataSourceName + " to ThreadLocal, request method name is : "+method);
            }else{
                DynamicDataSourceContextHolder.setDataSource(DataSourceKey.DATA_SOURCE_MAIN);
                logger.info("use default datasource , request method name is : "+method);
            }
        }catch (Exception e){
            logger.info("current thread " + Thread.currentThread().getName() + " add data to ThreadLocal error", e);
        }
    }


    /**
     * 执行完切面后，将线程共享中的数据源名称清空，数据源恢复为原来的默认数据源
     */
    @After("dataSourcePointCut()")
    public void after(JoinPoint joinPoint){
        DynamicDataSourceContextHolder.clear();
    }

}
