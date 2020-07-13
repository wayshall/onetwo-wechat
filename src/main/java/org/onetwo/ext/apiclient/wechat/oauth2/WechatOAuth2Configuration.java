package org.onetwo.ext.apiclient.wechat.oauth2;

import org.onetwo.ext.apiclient.wechat.core.DefaultWechatConfig.Oauth2Properties;
import org.onetwo.ext.apiclient.wechat.oauth2.api.WechatOauth2Client;
import org.onetwo.ext.apiclient.wechat.oauth2.api.WechatOauth2CustomImpl;
import org.onetwo.ext.apiclient.wechat.serve.service.HttpRequestStoreService;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@ConditionalOnProperty(name=Oauth2Properties.ENABLED_KEY, havingValue="true", matchIfMissing=false)
public class WechatOAuth2Configuration {

	/*@Bean
	public OAuth2UserInfoArgumentResolver oauth2UserInfoArgumentResolver(){
		return new OAuth2UserInfoArgumentResolver();
	}*/

	@Bean
	@ConditionalOnMissingBean(WechatOAuth2Hanlder.class)
	public WechatOAuth2Hanlder wechatOAuth2Hanlder(WechatOauth2Client wechatOauth2Client){
		WechatOAuth2Hanlder handler = new WechatOAuth2Hanlder();
		handler.setWechatOAuth2UserRepository(wechatOauth2UserStoreService());
		handler.setWechatOauth2Client(wechatOauth2Client);
		return handler;
	}
	
	@Bean
	@ConditionalOnMissingBean(WechatOAuth2UserRepository.class)
	public WechatOAuth2UserRepository<OAuth2LoginInfo> wechatOauth2UserStoreService(){
		return new HttpRequestStoreService<OAuth2LoginInfo>();
//		return new HtppSessionStoreService<OAuth2UserInfo>();
	}
	
	@Bean
	public WechatOauth2CustomImpl wechatOauth2Custom() {
		return new WechatOauth2CustomImpl();
	}
}
