package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.common.spring.Springs;
import org.onetwo.ext.apiclient.wechat.support.impl.AccessTokenServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@ComponentScan(basePackageClasses=AccessTokenServiceImpl.class)
public class WechatSupportConfiguration implements ApplicationContextAware {

	
	@Bean
	public WechatConfig wechatConfig(){
		return new DefaultWechatConfig();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Springs.initApplicationIfNotInitialized(applicationContext);
	}
	
}
