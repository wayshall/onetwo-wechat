package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.common.spring.Springs;
import org.onetwo.ext.apiclient.wechat.dbm.service.AccessTokenRepository;
import org.onetwo.ext.apiclient.wechat.dbm.service.DbStoreAccessTokenService;
import org.onetwo.ext.apiclient.wechat.event.WechatEventBus;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.support.impl.MemoryAccessTokenService;
import org.onetwo.ext.apiclient.wechat.support.impl.RedisStoreAccessTokenService;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.WechatConfigKeys;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@EnableScheduling
public class WechatSupportConfiguration implements ApplicationContextAware {
	
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
	
	@Configuration
	@ConditionalOnProperty(name=WechatConfigKeys.STORER_KEY, havingValue=WechatConfigKeys.STORER_DATABASE_KEY)
	protected static class DatabaseConfiguration {

		
		@Bean
		public AccessTokenService dbStoreAccessTokenService(WechatConfig wechatConfig, AccessTokenRepository accessTokenRepository){
			DbStoreAccessTokenService service = new DbStoreAccessTokenService(accessTokenRepository);
			return service;
		}
		
		@Bean
		public AccessTokenRepository accessTokenRepository() {
			return new AccessTokenRepository();
		}
	}

	@Configuration
	@EnableConfigurationProperties(DefaultWechatConfig.class)
	@ConditionalOnMissingBean(WechatConfigProvider.class)
	protected static class DefaultWechatConfigConfiguration {
		@Autowired
		private DefaultWechatConfig wechatConfig;
		
		@Bean
		@Primary
		public WechatConfig wechatConfig(){
			return wechatConfig;
		}
		
		@Bean
		public WechatConfigProvider wechatConfigProvider(){
			return new SimpleWechatConfigProvider();
		}
	}
	
	/*@Bean
	@ConditionalOnMissingBean(RedisRefreshAccessTokenTask.class)
	@ConditionalOnProperty(name=WechatConfigKeys.ENABLED_TASK_REFRESHTOKEN_KEY, havingValue="true", matchIfMissing=false)
	public RedisRefreshAccessTokenTask redisRefreshAccessTokenTask(){
		return new RedisRefreshAccessTokenTask();
	}*/
	
	@Bean
	public WechatEventBus wechatEventBus() {
		return new WechatEventBus();
	}
	
}
