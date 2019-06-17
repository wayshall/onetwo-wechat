package org.onetwo.ext.apiclient.qcloud.nlp;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
@EnableConfigurationProperties(NlpProperties.class)
public class NlpConfiguration {
	
	@Bean
	public static NlpApiClentRegistrar nlpApiClentRegistrar() {
		return new NlpApiClentRegistrar();
	}

}
