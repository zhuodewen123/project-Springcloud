package com.zhuodewen.www;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Import(CommonApplication.class)
@PropertySource("classpath:application-member.properties") //配置文件需改名,因为和引入的common模块冲突了
@EnableEurekaClient                                        //Eureka的消费端(SpringCloud生产者)
@EnableHystrix                                             //熔断器
@MapperScan("com.zhuodewen.www.mapper") 				   //mapper扫描器
@EnableTransactionManagement                               //事务管理器
//@EnableElasticsearchRepositories(basePackages = "com.zhuodewen.www")	//开启扫描搜索引擎的注解
public class MemberserverApplication {

	/**
	 * gateway网关配置(配置方式二:代码配置,还有一种是properties/yml配置)
	 * @param builder
	 * @return
	 */
	/*@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/qq*//**")
						.and().uri("http://www.qq.com/"))
				.build();
	}*/

	public static void main(String[] args) {
		//ES6.5.2和SpringBoot2.0.4的netty会冲入,启动时先把es的netty去掉
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(MemberserverApplication.class, args);
	}

}
