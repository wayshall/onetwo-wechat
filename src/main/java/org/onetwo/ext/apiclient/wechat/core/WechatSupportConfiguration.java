package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.common.spring.Springs;
import org.onetwo.ext.apiclient.wechat.support.impl.MemoryAccessTokenService;
import org.onetwo.ext.apiclient.wechat.support.impl.RedisStoreAccessTokenService;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.WechatConfigKeys;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@ComponentScan(basePackageClasses=MemoryAccessTokenService.class)
@EnableConfigurationProperties(DefaultWechatConfig.class)
@EnableScheduling
public class WechatSupportConfiguration implements ApplicationContextAware {

	@Autowired
	private DefaultWechatConfig wechatConfig;
	
	@Bean
	@Primary
	public WechatConfig wechatConfig(){
		return wechatConfig;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Springs.initApplicationIfNotInitialized(applicationContext);
	}
	
	@Bean
	@ConditionalOnProperty(name=WechatConfigKeys.STORER_KEY, havingValue=WechatConfigKeys.STORER_MEMORY_KEY, matchIfMissing=true)
	public AccessTokenService memoryAccessTokenService(){
		return new MemoryAccessTokenService();
	}
	
	@Bean
	@ConditionalOnProperty(name=WechatConfigKeys.STORER_KEY, havingValue=WechatConfigKeys.STORER_REDIS_KEY)
	public AccessTokenService redisStoreAccessTokenService(){
		return new RedisStoreAccessTokenService();
	}
	
	/*@Bean
	@ConditionalOnMissingBean(RedisRefreshAccessTokenTask.class)
	@ConditionalOnProperty(name=WechatConfigKeys.ENABLED_TASK_REFRESHTOKEN_KEY, havingValue="true", matchIfMissing=false)
	public RedisRefreshAccessTokenTask redisRefreshAccessTokenTask(){
		return new RedisRefreshAccessTokenTask();
	}*/
	
}
