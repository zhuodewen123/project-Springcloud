package com.zhuodewen.www.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置类
 */
@Configuration
@MapperScan("com.zhuodewen.www.mapper*")
public class MybatisPlusConfig {

    /*
	* 分页插件，自动识别数据库类型
	 */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

	/*
	* oracle数据库配置JdbcTypeForNull
	* 参考：https://gitee.com/baomidou/mybatisplus-boot-starter/issues/IHS8X
	不需要这样配置了，参考 yml:
		mybatis-plus:
			confuguration
				dbc-type-for-null: 'null'
	@Bean
	 public ConfigurationCustomizer configurationCustomizer(){
		return new MybatisPlusCustomizers();
	 }
	class MybatisPlusCustomizers implements ConfigurationCustomizer {
		@Override
		public void customize(org.apache.ibatis.session.Configuration configuration) {
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		}
	} */
}
