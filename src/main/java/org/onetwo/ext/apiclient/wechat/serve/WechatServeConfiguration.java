package org.onetwo.ext.apiclient.wechat.serve;

import org.onetwo.common.spring.condition.OnMissingBean;
import org.onetwo.ext.apiclient.wechat.serve.controller.ServeController;
import org.onetwo.ext.apiclient.wechat.serve.service.MessageRouterServiceImpl;
import org.onetwo.ext.apiclient.wechat.serve.spi.ServeEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@ComponentScan(basePackageClasses=MessageRouterServiceImpl.class)
public class WechatServeConfiguration {
	
	@Bean
//	@Conditional(MissingServeEndpointCondition.class)
	@OnMissingBean(ServeEndpoint.class)
	public ServeEndpoint serveEndpoint(){
		return new ServeController();
	}

}
