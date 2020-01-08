package com.zhuodewen.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    /**
     * 创建swagger2的主配置对象
     * @return
     */
    @Bean
    public Docket getDocker(){
        return new Docket(DocumentationType.SWAGGER_2).select().build();
    }
}
