package com.teligen.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Value( "${swaggerShow.show}" )
    private boolean swaggerShow;

    /**
     * 创建API基本信息
     * @return
     */
    @Bean
    public Docket createRestApi() {
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> params = new ArrayList<Parameter>();
        ticketPar.name("token").description("警务token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build(); //header中的token参数必填
        params.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        return new Docket( DocumentationType.SWAGGER_2)
                .globalOperationParameters(params)
                .apiInfo(apiInfo())
                .select()
                //扫描需要展示的api接口
                .apis( RequestHandlerSelectors.basePackage("com.teligen.demo.controller"))
                .paths( swaggerShow? PathSelectors.any():PathSelectors.none())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("xx项目 RESTful APIs")
                .description("xx项目后台api接口文档")
                .version("1.0")
                .build();
    }
}
