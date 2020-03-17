package org.onetwo.ext.apiclient.wechat.accesstoken;

import java.util.List;

import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AppCacheKeyGenerator;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.core.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.dbm.service.AccessTokenRepository;
import org.onetwo.ext.apiclient.wechat.dbm.service.DbStoreAccessTokenService;
import org.onetwo.ext.apiclient.wechat.support.impl.MemoryAccessTokenService;
import org.onetwo.ext.apiclient.wechat.support.impl.RedisStoreAccessTokenService;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.WechatConfigKeys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
public class AccessTokenConfiguration implements InitializingBean {
	
	@Autowired
	private List<AccessTokenProvider> accessTokenProviders;
	
	private CombineAccessTokenProvider accessTokenProvider;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.accessTokenProvider = combineAccessTokenProvider();
	}
	
	public CombineAccessTokenProvider combineAccessTokenProvider() {
		CombineAccessTokenProvider accessTokenProvider = new CombineAccessTokenProvider();
		accessTokenProvider.setAccessTokenProviders(accessTokenProviders);
		return accessTokenProvider;
	}
	
	@Bean
	public AppCacheKeyGenerator appCacheKeyGenerator() {
		WechatAppCacheKeyGenerator g = new WechatAppCacheKeyGenerator();
		return g;
	}
	
	@Bean
	@ConditionalOnProperty(name=WechatConfigKeys.STORER_KEY, havingValue=WechatConfigKeys.STORER_MEMORY_KEY, matchIfMissing=true)
	public AccessTokenService memoryAccessTokenService(WechatConfigProvider wechatConfigProvider){
		MemoryAccessTokenService service = new MemoryAccessTokenService();
		service.setAccessTokenProvider(accessTokenProvider);
		service.setWechatConfigProvider(wechatConfigProvider);
		return service;
	}
	
	@Bean
	@ConditionalOnProperty(name=WechatConfigKeys.STORER_KEY, havingValue=WechatConfigKeys.STORER_REDIS_KEY)
	public AccessTokenService redisStoreAccessTokenService(WechatConfigProvider wechatConfigProvider){
		RedisStoreAccessTokenService tokenService = new RedisStoreAccessTokenService();
		tokenService.setAccessTokenProvider(accessTokenProvider);
		tokenService.setWechatConfigProvider(wechatConfigProvider);
		return tokenService;
	}
	
	@Bean
	@ConditionalOnProperty(name=WechatConfigKeys.STORER_KEY, havingValue=WechatConfigKeys.STORER_DATABASE_KEY)
	public AccessTokenService dbStoreAccessTokenService(WechatConfig wechatConfig, 
														AccessTokenRepository accessTokenRepository,
//														WechatAccessTokenProvider accessTokenProvider,
														WechatConfigProvider wechatConfigProvider){
		DbStoreAccessTokenService tokenService = new DbStoreAccessTokenService(accessTokenRepository);
		tokenService.setAccessTokenProvider(accessTokenProvider);
		tokenService.setWechatConfigProvider(wechatConfigProvider);
		return tokenService;
	}
	
	@Bean
	@ConditionalOnProperty(name=WechatConfigKeys.STORER_KEY, havingValue=WechatConfigKeys.STORER_DATABASE_KEY)
	public AccessTokenRepository accessTokenRepository() {
		return new AccessTokenRepository();
	}

}
