package org.onetwo.ext.apiclient.work.core;

import org.onetwo.common.spring.Springs;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@EnableScheduling
@ConditionalOnProperty(value = WorkWechatConfig.ENABLED_KEY, matchIfMissing = true)
public class WorkWechatSupportConfiguration implements ApplicationContextAware {
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Springs.initApplicationIfNotInitialized(applicationContext);
	}
	
	@Bean
	public WorkWechatAccessTokenProvider workWechatAccessTokenProvider() {
		return new WorkWechatAccessTokenProvider();
	}
	
	
}
