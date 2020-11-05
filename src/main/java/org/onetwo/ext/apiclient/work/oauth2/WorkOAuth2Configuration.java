package org.onetwo.ext.apiclient.work.oauth2;

import org.onetwo.ext.apiclient.wechat.serve.service.HttpRequestStoreService;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.onetwo.ext.apiclient.work.core.WorkConfigProvider;
import org.onetwo.ext.apiclient.work.core.WorkWechatConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@ConditionalOnProperty(value = WorkWechatConfig.ENABLED_KEY, matchIfMissing = true)
public class WorkOAuth2Configuration {

	@Primary
	@Bean
	@ConditionalOnMissingBean(WorkWechatOAuth2Hanlder.class)
	public WorkWechatOAuth2Hanlder workWechatOAuth2Hanlder(WechatOAuth2UserRepository<WorkUserLoginInfo> oauth2UserRepository, WorkConfigProvider workConfigProvider){
		WorkWechatOAuth2Hanlder handler = new WorkWechatOAuth2Hanlder();
		handler.setWechatOAuth2UserRepository(oauth2UserRepository);
		handler.setWorkConfigProvider(workConfigProvider);
		return handler;
	}
	
	@Bean
	@ConditionalOnMissingBean(WorkQRConnectHandler.class)
	public WorkQRConnectHandler workQRConnectHandler(WechatOAuth2UserRepository<WorkUserLoginInfo> oauth2UserRepository, WorkConfigProvider workConfigProvider){
		WorkQRConnectHandler handler = new WorkQRConnectHandler();
		handler.setWechatOAuth2UserRepository(oauth2UserRepository);
		handler.setWorkConfigProvider(workConfigProvider);
		return handler;
	}
	
	@Bean
	@ConditionalOnMissingBean(WechatOAuth2UserRepository.class)
	public WechatOAuth2UserRepository<WorkUserLoginInfo> workOauth2UserStoreService(){
		return new HttpRequestStoreService<WorkUserLoginInfo>();
	}
	
}
