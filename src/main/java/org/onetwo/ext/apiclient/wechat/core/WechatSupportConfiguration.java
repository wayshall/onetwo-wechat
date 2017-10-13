package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.boot.core.web.mvc.interceptor.MvcInterceptor;
import org.onetwo.boot.core.web.mvc.interceptor.MvcInterceptorManager;
import org.onetwo.common.spring.Springs;
import org.onetwo.ext.apiclient.wechat.boot.WechatOAuth2MvcInterceptor;
import org.onetwo.ext.apiclient.wechat.serve.web.WechatOAuth2Hanlder;
import org.onetwo.ext.apiclient.wechat.support.impl.MemoryAccessTokenService;
import org.onetwo.ext.apiclient.wechat.support.impl.RedisRefreshAccessTokenTask;
import org.onetwo.ext.apiclient.wechat.support.impl.RedisStoreAccessTokenService;
import org.onetwo.ext.apiclient.wechat.utils.OAuth2UserInfoArgumentResolver;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.AccessTokenKeys;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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

	
	/*@Bean
	public WechatConfig wechatConfig(){
		return new DefaultWechatConfig();
	}*/

	/****
	 * 拦截器
	 * @author wayshall
	 * @return
	 */
	@Bean
	@ConditionalOnBean({MvcInterceptorManager.class, WechatOAuth2Hanlder.class})
	public MvcInterceptor wechatOAuth2MvcInterceptor(){
		return new WechatOAuth2MvcInterceptor();
	}
	
	@Bean
	public OAuth2UserInfoArgumentResolver oauth2UserInfoArgumentResolver(){
		return new OAuth2UserInfoArgumentResolver();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Springs.initApplicationIfNotInitialized(applicationContext);
	}
	
	@Bean
	@ConditionalOnProperty(name=AccessTokenKeys.STORER_KEY, havingValue=AccessTokenKeys.STORER_MEMORY_KEY, matchIfMissing=true)
	public AccessTokenService memoryAccessTokenService(){
		return new MemoryAccessTokenService();
	}
	
	@Bean
	@ConditionalOnProperty(name=AccessTokenKeys.STORER_KEY, havingValue=AccessTokenKeys.STORER_REDIS_KEY)
	public AccessTokenService redisStoreAccessTokenService(){
		return new RedisStoreAccessTokenService();
	}
	
	@Bean
	@ConditionalOnProperty(name=AccessTokenKeys.STORER_KEY, havingValue=AccessTokenKeys.STORER_REDIS_KEY)
	public RedisRefreshAccessTokenTask redisRefreshAccessTokenTask(){
		return new RedisRefreshAccessTokenTask();
	}
	
}
