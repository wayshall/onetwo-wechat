package org.onetwo.ext.apiclient.wechat.serve;

import org.onetwo.ext.apiclient.wechat.core.DefaultWechatConfig;
import org.onetwo.ext.apiclient.wechat.serve.service.HtppSessionStoreService;
import org.onetwo.ext.apiclient.wechat.serve.service.MessageRouterServiceImpl;
import org.onetwo.ext.apiclient.wechat.serve.spi.ServeEndpoint;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatSessionRepository;
import org.onetwo.ext.apiclient.wechat.serve.web.ServeController;
import org.onetwo.ext.apiclient.wechat.serve.web.WechatOAuth2Hanlder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@ComponentScan(basePackageClasses=MessageRouterServiceImpl.class)
@EnableConfigurationProperties(DefaultWechatConfig.class)
public class WechatServeConfiguration  {
	
	
	@Bean
//	@Conditional(MissingServeEndpointCondition.class)
	@ConditionalOnMissingBean(ServeEndpoint.class)
	public ServeEndpoint serveEndpoint(){
		return new ServeController();
	}
	

	@Bean
	@ConditionalOnMissingBean(WechatOAuth2Hanlder.class)
	public WechatOAuth2Hanlder wechatOAuth2Hanlder(){
		return new WechatOAuth2Hanlder();
	}
	
	@Bean
	@ConditionalOnMissingBean(WechatSessionRepository.class)
	public WechatSessionRepository sessionStoreService(){
		return new HtppSessionStoreService();
	}
	
}
