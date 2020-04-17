package com.teligen.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalConfig {

    @Value("${tomcat_max_file_size:300MB}")
    private String tomcat_max_file_size;


    @Value("${tomcat_max_request_size:500MB}")
    private String tomcat_max_request_size;


    public String getTomcat_max_file_size() {
        return tomcat_max_file_size;
    }

    public void setTomcat_max_file_size(String tomcat_max_file_size) {
        this.tomcat_max_file_size = tomcat_max_file_size;
    }

    public String getTomcat_max_request_size() {
        return tomcat_max_request_size;
    }

    public void setTomcat_max_request_size(String tomcat_max_request_size) {
        this.tomcat_max_request_size = tomcat_max_request_size;
    }
}
