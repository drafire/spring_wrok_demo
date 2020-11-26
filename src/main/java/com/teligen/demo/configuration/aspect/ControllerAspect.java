package com.teligen.demo.configuration.aspect;

import com.alibaba.fastjson.JSONObject;
import com.teligen.demo.utils.CommonUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Map;

@Aspect
@Component
@Order(1)
public class ControllerAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    /**
     * 对controller下的包均进行拦截处理
     * **/
    @Pointcut("execution(* com.teligen.demo.controller..*.*(..))")
    public void postController(){

    }

    @Before("postController()")
    public void beforePost(){
        //请求前
    }

    @After("postController()")
    public void afterPost() {
        //请求后处理
    }

    @Around("postController()")
    public Object aroundPost(ProceedingJoinPoint pjd){
        StopWatch clock = new StopWatch();
        clock.start();

        /******** 获取请求数据  **********/
        StringBuilder logInfo = new StringBuilder();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        JSONObject headParams = new JSONObject();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headParams.put(headerName, headerValue);
        }
        StringBuffer url = request.getRequestURL();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();
        logInfo.append("客户端IP：" + ip + ";");
        logInfo.append("请求路径：" + url + ";");
        logInfo.append("请求方法：" + method + ";\n");
        logInfo.append("请求头参数详情：");
        logInfo.append(headParams.toJSONString() + "\n");
        logInfo.append("表单参数详情：");
        Map<String, String[]> paramsMap = request.getParameterMap();
        for (String key : paramsMap.keySet()) {
            String[] values = paramsMap.get(key);
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                logInfo.append(key + "：" + value + ";");
            }
        }
        logInfo.append("\n");

        logInfo.append("POST参数详情：");
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = request.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            logInfo.append(sb.toString());
        } catch (IOException e) {
            logInfo.append("参数获取异常：" + e.getMessage());
        }
        logInfo.append("\n");
        /******** 获取请求数据  **********/

        Object result = null;
        if(!"".equals(CommonUtils.safeToString(url))){
            try {
                result = pjd.proceed();
            } catch (Throwable throwable) {
                clock.stop();
                logInfo.append("请求处理异常：" + throwable.toString() + "\n");

                JSONObject responseMap = new JSONObject();
                responseMap.put("result", "0");
                responseMap.put("errorCode", "-99");
                responseMap.put("message", "服务处理异常");
                result = responseMap.toJSONString();
                logger.info(logInfo.toString());
                return result;
            }
        }
        clock.stop();
        if(result == null || !(result instanceof Map)){
            logInfo.append("非常规WEB请求,执行结束,耗时：：" + clock.getTime() + "ms\n");
        } else {
            logInfo.append("执行结束，返回结果：");            
            String finalResult = JSON.toJSONString(result);
            //截取字符串，防止打印日志过长
            finalResult = finalResult.length() > 2000 ? finalResult.substring(0, 2000) : finalResult;
            logInfo.append("执行结束，返回结果：" + JSON.toJSONString(finalResult) + "\n");
            logInfo.append("WEB请求执行结束,耗时：" + clock.getTotalTimeMillis() + "ms");
        }
        long costTime = clock.getTime();
        //logInfo.append("请求时长: " + String.format("%.2f", costTime/1000f) + "s\n");
        logInfo.append("请求时长: " + costTime + "(ms)\n");
        logger.info(logInfo.toString());
        return result;
    }
}
