package org.onetwo.ext.apiclient.work.oauth2;

import org.onetwo.ext.apiclient.wechat.serve.service.HttpRequestStoreService;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
//@ConditionalOnProperty(name=Oauth2Properties.ENABLED_KEY, havingValue="true", matchIfMissing=false)
public class WorkOAuth2Configuration {

	
	@Bean
	@ConditionalOnMissingBean(WorkWechatOAuth2Hanlder.class)
	public WorkWechatOAuth2Hanlder workWechatOAuth2Hanlder(WechatOAuth2UserRepository<WorkUserLoginInfo> oauth2UserRepository, WechatConfigProvider wechatConfigProvider){
		WorkWechatOAuth2Hanlder handler = new WorkWechatOAuth2Hanlder();
		handler.setWechatOAuth2UserRepository(oauth2UserRepository);
		handler.setWechatConfigProvider(wechatConfigProvider);
		return handler;
	}
	
	@Bean
	@ConditionalOnMissingBean(WorkQRConnectHandler.class)
	public WorkQRConnectHandler workQRConnectHandler(WechatOAuth2UserRepository<WorkUserLoginInfo> oauth2UserRepository, WechatConfigProvider wechatConfigProvider){
		WorkQRConnectHandler handler = new WorkQRConnectHandler();
		handler.setWechatOAuth2UserRepository(oauth2UserRepository);
		handler.setWechatConfigProvider(wechatConfigProvider);
		return handler;
	}
	
	@Bean
	@ConditionalOnMissingBean(WechatOAuth2UserRepository.class)
	public WechatOAuth2UserRepository<WorkUserLoginInfo> sessionStoreService(){
		return new HttpRequestStoreService<WorkUserLoginInfo>();
	}
	
	
}
