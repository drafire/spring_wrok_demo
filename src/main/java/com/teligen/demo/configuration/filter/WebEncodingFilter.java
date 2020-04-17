package com.teligen.demo.configuration.filter;

import com.teligen.demo.configuration.EncodingRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "webEncodingFilter", urlPatterns = "/*")
public class WebEncodingFilter implements Filter{

    private static final Logger logger = LoggerFactory.getLogger(WebEncodingFilter.class);
    private String charset = "UTF-8";
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        //设置请求响应字符编码
        servletRequest.setCharacterEncoding(charset);
        servletResponse.setCharacterEncoding(charset);
        //新增加的代码
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if ("OPTIONS".equals(httpServletRequest.getMethod())){
            logger.info("options请求");
            httpServletResponse.setStatus(200);
        }

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
//        logger.info("设置请求头可访问地址完成");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        logger.info("设置请求头可访问方法完成");
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");

        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With,token");
//        logger.info("设置请求头可添加属性完成");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");// 允许cookie
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));

        if(httpServletRequest.getMethod().equalsIgnoreCase("get")) {
            httpServletRequest = new EncodingRequestWrapper(httpServletRequest,charset);
        }

//        logger.warn("----请求被"+filterConfig.getFilterName()+"过滤");
        //传递给目标servlet或jsp的实际上时包装器对象的引用，而不是原始的HttpServletRequest对象
        filterChain.doFilter(httpServletRequest, servletResponse);
//        logger.warn("----响应被"+filterConfig.getFilterName()+"过滤");
    }

    @Override
    public void destroy() {

    }
}
