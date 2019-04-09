package org.onetwo.ext.apiclient.wechat.oauth2;

import org.onetwo.boot.core.web.mvc.interceptor.MvcInterceptor;
import org.onetwo.boot.core.web.mvc.interceptor.MvcInterceptorManager;
import org.onetwo.ext.apiclient.wechat.boot.WechatOAuth2MvcInterceptor;
import org.onetwo.ext.apiclient.wechat.core.DefaultWechatConfig.Oauth2Properties;
import org.onetwo.ext.apiclient.wechat.oauth2.api.WechatOauth2CustomImpl;
import org.onetwo.ext.apiclient.wechat.serve.service.HtppSessionStoreService;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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

	/****
	 * 拦截器
	 * @author wayshall
	 * @return
	 */
	@Bean
	@ConditionalOnBean({MvcInterceptorManager.class, WechatOAuth2Hanlder.class})
	public MvcInterceptor wechatOAuth2MvcInterceptor(WechatOAuth2Hanlder oauth2Hanlder){
		WechatOAuth2MvcInterceptor interceptor = new WechatOAuth2MvcInterceptor();
		interceptor.setOAuth2Hanlder(oauth2Hanlder);
		return interceptor;
	}
	
	@Bean
	public OAuth2UserInfoArgumentResolver oauth2UserInfoArgumentResolver(){
		return new OAuth2UserInfoArgumentResolver();
	}

	@Bean
	@ConditionalOnMissingBean(WechatOAuth2Hanlder.class)
	public WechatOAuth2Hanlder wechatOAuth2Hanlder(){
		return new WechatOAuth2Hanlder();
	}
	
	@Bean
	@ConditionalOnMissingBean(WechatOAuth2UserRepository.class)
	public WechatOAuth2UserRepository<OAuth2UserInfo> sessionStoreService(){
		return new HtppSessionStoreService<OAuth2UserInfo>();
	}
	
	@Bean
	public WechatOauth2CustomImpl wechatOauth2Custom() {
		return new WechatOauth2CustomImpl();
	}
}
