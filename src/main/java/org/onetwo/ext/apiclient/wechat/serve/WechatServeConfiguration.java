package org.onetwo.ext.apiclient.wechat.serve;

import org.onetwo.common.spring.condition.OnMissingBean;
import org.onetwo.ext.apiclient.wechat.serve.service.HtppSessionStoreService;
import org.onetwo.ext.apiclient.wechat.serve.service.MessageRouterServiceImpl;
import org.onetwo.ext.apiclient.wechat.serve.spi.ServeEndpoint;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatUserStoreService;
import org.onetwo.ext.apiclient.wechat.serve.web.ServeController;
import org.onetwo.ext.apiclient.wechat.serve.web.WechatOAuth2Hanlder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@ComponentScan(basePackageClasses=MessageRouterServiceImpl.class)
public class WechatServeConfiguration  {
	
	
	@Bean
//	@Conditional(MissingServeEndpointCondition.class)
	@OnMissingBean(ServeEndpoint.class)
	public ServeEndpoint serveEndpoint(){
		return new ServeController();
	}
	

	@Bean
	@OnMissingBean(WechatOAuth2Hanlder.class)
	public WechatOAuth2Hanlder wechatOAuth2Hanlder(){
		return new WechatOAuth2Hanlder();
	}
	
	@Bean
	@OnMissingBean(WechatUserStoreService.class)
	public WechatUserStoreService sessionStoreService(){
		return new HtppSessionStoreService();
	}
	
}
