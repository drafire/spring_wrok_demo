package com.teligen.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class TomcatConfig {

    @Autowired
    private GlobalConfig globalConfig;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(globalConfig.getTomcat_max_file_size()); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize(globalConfig.getTomcat_max_request_size());
        return factory.createMultipartConfig();
    }
}
