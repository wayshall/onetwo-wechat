package org.onetwo.ext.apiclient.work.core;

import org.onetwo.common.spring.Springs;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.core.SimpleWechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.dbm.service.AccessTokenRepository;
import org.onetwo.ext.apiclient.wechat.dbm.service.DbStoreAccessTokenService;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.support.impl.MemoryAccessTokenService;
import org.onetwo.ext.apiclient.wechat.support.impl.RedisStoreAccessTokenService;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.WechatConfigKeys;
import org.onetwo.ext.apiclient.work.utils.WorkWechatConstants.WorkWechatConfigKeys;
import org.onetwo.ext.apiclient.wxcommon.AccessTokenProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@EnableScheduling
public class WorkWechatSupportConfiguration implements ApplicationContextAware {
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Springs.initApplicationIfNotInitialized(applicationContext);
	}
	
	@Bean
	public WorkWechatAccessTokenProvider wechatAccessTokenProvider() {
		return new WorkWechatAccessTokenProvider();
	}
	
	@Bean
	@ConditionalOnProperty(name=WorkWechatConfigKeys.STORER_KEY, havingValue=WechatConfigKeys.STORER_MEMORY_KEY, matchIfMissing=true)
	public AccessTokenService workMemoryAccessTokenService(AccessTokenProvider accessTokenProvider, WechatConfigProvider wechatConfigProvider){
		MemoryAccessTokenService service = new MemoryAccessTokenService();
		service.setAccessTokenProvider(accessTokenProvider);
		service.setWechatConfigProvider(wechatConfigProvider);
		return service;
	}
	
	@Bean
	@ConditionalOnProperty(name=WorkWechatConfigKeys.STORER_KEY, havingValue=WechatConfigKeys.STORER_REDIS_KEY)
	public AccessTokenService workRedisStoreAccessTokenService(AccessTokenProvider accessTokenProvider, WechatConfigProvider wechatConfigProvider){
		RedisStoreAccessTokenService tokenService = new RedisStoreAccessTokenService();
		tokenService.setAccessTokenProvider(accessTokenProvider);
		tokenService.setWechatConfigProvider(wechatConfigProvider);
		return tokenService;
	}
	
	@Configuration
	@ConditionalOnProperty(name=WorkWechatConfigKeys.STORER_KEY, havingValue=WechatConfigKeys.STORER_DATABASE_KEY)
	protected static class WorkDatabaseConfiguration {

		
		@Bean
		public AccessTokenService workDbStoreAccessTokenService(WechatConfig wechatConfig, 
															AccessTokenRepository accessTokenRepository,
															AccessTokenProvider accessTokenProvider,
															WechatConfigProvider wechatConfigProvider){
			DbStoreAccessTokenService tokenService = new DbStoreAccessTokenService(accessTokenRepository);
			tokenService.setAccessTokenProvider(accessTokenProvider);
			tokenService.setWechatConfigProvider(wechatConfigProvider);
			return tokenService;
		}
		
		@Bean
		public AccessTokenRepository workAccessTokenRepository() {
			return new AccessTokenRepository();
		}
	}

	@Configuration
	@EnableConfigurationProperties(WorkWechatConfig.class)
	@ConditionalOnMissingBean(WechatConfigProvider.class)
	protected static class WorkWechatConfigConfiguration {
		@Autowired
		private WorkWechatConfig wechatConfig;
		
		@Bean
		@Primary
		public WorkWechatConfig workWechatConfig(){
			return wechatConfig;
		}
		
		@Bean
		public WechatConfigProvider workWechatConfigProvider(){
			WorkWechatConfigProvider provider = new WorkWechatConfigProvider(wechatConfig);
			return provider;
		}
	}
	
	public static class WorkWechatConfigProvider extends SimpleWechatConfigProvider {
		private WorkWechatConfig workWechatConfig;
		
		public WorkWechatConfigProvider(WorkWechatConfig workWechatConfig) {
			super();
			this.workWechatConfig = workWechatConfig;
		}

		@Override
		public WechatConfig getWechatConfig(String clientId) {
			return workWechatConfig.getWechatConfig(clientId);
		}
	}
	
	
}
