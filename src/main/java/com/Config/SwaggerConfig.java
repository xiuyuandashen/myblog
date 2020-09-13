package com.Config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.controller"))
                .build();
    }

    public ApiInfo apiInfo(){
        Contact contact = new Contact("zlf","xiuyuandashen.org","1556450877@qq.com");
        return new ApiInfo("博客系统","xiuyuandashen的博客","v1.0","无",contact,"许可证","许可证网址",new ArrayList<>());
    }
}
