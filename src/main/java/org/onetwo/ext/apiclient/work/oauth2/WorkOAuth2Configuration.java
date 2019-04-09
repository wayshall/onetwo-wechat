package org.onetwo.ext.apiclient.work.oauth2;

import org.onetwo.boot.core.web.mvc.interceptor.MvcInterceptor;
import org.onetwo.boot.core.web.mvc.interceptor.MvcInterceptorManager;
import org.onetwo.ext.apiclient.wechat.boot.WechatOAuth2MvcInterceptor;
import org.onetwo.ext.apiclient.wechat.oauth2.WechatOAuth2Hanlder;
import org.onetwo.ext.apiclient.wechat.serve.service.HtppSessionStoreService;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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

	/****
	 * 拦截器
	 * @author wayshall
	 * @return
	 */
	@Bean
	@ConditionalOnBean({MvcInterceptorManager.class, WorkWechatOAuth2Hanlder.class})
	public MvcInterceptor workWechatOAuth2MvcInterceptor(WechatOAuth2Hanlder oauth2Hanlder){
		WechatOAuth2MvcInterceptor interceptor = new WechatOAuth2MvcInterceptor();
		interceptor.setOAuth2Hanlder(oauth2Hanlder);
		return interceptor;
	}
	
	@Bean
	@ConditionalOnMissingBean(WorkWechatOAuth2Hanlder.class)
	public WorkWechatOAuth2Hanlder workWechatOAuth2Hanlder(){
		return new WorkWechatOAuth2Hanlder();
	}
	
	@Bean
	@ConditionalOnMissingBean(WechatOAuth2UserRepository.class)
	public WechatOAuth2UserRepository sessionStoreService(){
		return new HtppSessionStoreService();
	}
	
}
