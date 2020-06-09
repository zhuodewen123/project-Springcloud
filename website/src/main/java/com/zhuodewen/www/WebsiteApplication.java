package com.zhuodewen.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@PropertySource("classpath:application-website.properties")	//配置文件需改名,因为和引入的common模块冲突了
@Import(CommonApplication.class)
@EnableEurekaClient                                        	//Eureka的消费端(SpringCloud生产者)
@EnableHystrix                                            	//熔断器
@EnableSwagger2
public class WebsiteApplication extends SpringBootServletInitializer {

	//去除SpringBoot自带tomcat后,需加入该代码(即使用外部tomcat部署项目)
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebsiteApplication.class);	}


	public static void main(String[] args) {
		SpringApplication.run(WebsiteApplication.class, args);
	}

}
