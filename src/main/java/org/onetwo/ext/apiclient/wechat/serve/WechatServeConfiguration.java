package org.onetwo.ext.apiclient.wechat.serve;

import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.serve.service.WechatMessageRouterService;
import org.onetwo.ext.apiclient.wechat.serve.spi.ServeEndpoint;
import org.onetwo.ext.apiclient.wechat.serve.web.EventServeController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
//@ComponentScan(basePackageClasses=MessageRouterServiceImpl.class)
@ConditionalOnProperty(name=WechatConfig.ENABLE_MESSAGE_SERVE_KEY, matchIfMissing=true)
//@EnableConfigurationProperties(DefaultWechatConfig.class)
public class WechatServeConfiguration  {
	
	
	@Bean
//	@Conditional(MissingServeEndpointCondition.class)
	@ConditionalOnMissingBean(ServeEndpoint.class)
	public EventServeController serveEndpoint(){
		return new EventServeController();
	}
	
	@Bean
	@ConditionalOnMissingBean(WechatMessageRouterService.class)
	public WechatMessageRouterService wechatMessageRouterService(){
		WechatMessageRouterService service = new WechatMessageRouterService();
		return service;
	}
	
	
}
