package org.onetwo.ext.apiclient.wechat.serve;

import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.serve.controller.ServeController;
import org.onetwo.ext.apiclient.wechat.serve.service.BaseServeServiceImpl;
import org.onetwo.ext.apiclient.wechat.serve.spi.ServeEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@ComponentScan(basePackageClasses=BaseServeServiceImpl.class)
public class WechatServeConfiguration {
	
	@Bean
	@Conditional(MissingServeEndpointCondition.class)
	public ServeEndpoint serveEndpoint(){
		return new ServeController();
	}

	public static class MissingServeEndpointCondition implements Condition {

		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			String[] beanNames = context.getBeanFactory().getBeanNamesForType(ServeEndpoint.class);
			return LangUtils.isEmpty(beanNames);
		}
		
	}
}
