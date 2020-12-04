package com.teligen.demo.configuration;


import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class EncodingRequestWrapper extends HttpServletRequestWrapper {

    private String charset;

    public EncodingRequestWrapper(HttpServletRequest request, String charset) {
        super(request);
        this.charset = charset;
    }

    @Override
    public String getParameter(String name) {
        HttpServletRequest request = (HttpServletRequest) getRequest();

        String method = request.getMethod();
        if(method.equalsIgnoreCase("post")) {
            try {
                request.setCharacterEncoding(charset);
            } catch (UnsupportedEncodingException e) {}
        } else if(method.equalsIgnoreCase("get")) {
            String value = request.getParameter(name);
            try {
                value = new String(value.getBytes("ISO-8859-1"), charset);
            } catch (UnsupportedEncodingException e) {
            }
            return value;
        }
        return request.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> result = new HashMap<String, String[]>();

        HttpServletRequest request = (HttpServletRequest) getRequest();

        String method = request.getMethod();
        if(method.equalsIgnoreCase("get")) {
            Map<String, String[]> paramMap = request.getParameterMap();
            String newValue = null;
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                String[] value = entry.getValue();
                if (StrUtil.isNotEmpty(value[0])) {
                    try {
                        newValue = new String(value[0].getBytes("ISO-8859-1"), charset);
                    } catch (UnsupportedEncodingException e) {
                    }
                }
                else {
                    newValue = value[0];
                }
                result.put(entry.getKey(), new String[]{newValue});
            }
        }
        else {
            result = request.getParameterMap();
        }
        return result;
    }

}
