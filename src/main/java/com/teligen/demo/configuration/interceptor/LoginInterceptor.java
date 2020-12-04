package com.teligen.demo.configuration.interceptor;

import cn.hutool.core.util.StrUtil;

import com.teligen.demo.configuration.JedisCache;
import com.teligen.demo.dto.ResultCode;
import com.teligen.demo.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Resource
    private JedisCache jedis;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestUrl = request.getRequestURL().toString();
        if (!(requestUrl.endsWith("appLogin") || requestUrl.endsWith("appIdcardLogin")
                || requestUrl.endsWith("/swagger-resources")
                || requestUrl.endsWith("verifyAcode") || requestUrl.endsWith("/swagger-resources/configuration/ui"))) {//不拦截login
            String token = request.getHeader("token");
            //通过getHeaderNames获得所有头名字的Enumeration集合

            Enumeration<String> headNames = request.getHeaderNames();

            while (headNames.hasMoreElements()) {
                String headName = headNames.nextElement();
                //System.out.println(headName+":"+request.getHeader(headName));
                logger.warn(headName + ":" + request.getHeader(headName));
            }


            if (StrUtil.isNullOrUndefined(token)) {
                logger.warn("拦截掉" + request.getRemoteHost() + "的" + requestUrl + "请求:用户未登录");
                throw new BusinessException(ResultCode.EMPTY_TOKEN.getCode(), ResultCode.EMPTY_TOKEN.getDes());
            }

            if (!jedis.existKey(JedisCache.JSESSIONID_PREFIX + token)) {
                logger.warn("拦截掉" + request.getRemoteHost() + "的" + requestUrl + "请求:用户已断开连接");
                throw new BusinessException(ResultCode.TOKEN_EXPIRE.getCode(), ResultCode.TOKEN_EXPIRE.getDes());
            }

            String userInfo = (String) jedis.get(token);
            request.setAttribute("userInfo", userInfo);
            return true;
        } else {
            return true;
        }
        //return true;
    }
}
