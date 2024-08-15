package org.onetwo.ext.apiclient.qcloud.aiart;

import org.onetwo.ext.apiclient.qcloud.aiart.service.AiartService;
import org.onetwo.ext.apiclient.qcloud.aiart.service.impl.DefaultAiartService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({AiartProperties.class})
@ConditionalOnProperty(name=AiartProperties.ENABLE_KEY, matchIfMissing=false)
public class AiartConfiguration {
	
	@Bean
	public AiartService aiartService() {
		return new DefaultAiartService();
	}

}
