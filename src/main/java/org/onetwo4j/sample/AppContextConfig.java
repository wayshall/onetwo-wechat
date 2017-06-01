package org.onetwo4j.sample;

import org.onetwo.boot.module.security.config.EnableCommonSecurity;
import org.onetwo4j.sample.utils.DefaultWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCommonSecurity
public class AppContextConfig {
	
	public AppContextConfig(){
	}

	@Bean
	public DefaultWebSecurityConfigurerAdapter simpleWebSecurityConfigurerAdapter(){
		return new DefaultWebSecurityConfigurerAdapter();
	}
	
}
