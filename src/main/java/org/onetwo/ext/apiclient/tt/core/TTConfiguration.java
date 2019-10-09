package org.onetwo.ext.apiclient.tt.core;

import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
@EnableConfigurationProperties(TTAppConfig.class)
public class TTConfiguration {
	
	@Bean
	public AccessTokenProvider ttAccessTokenProvider() {
		TTAccessTokenProvider provider = new TTAccessTokenProvider();
		return provider;
	}

}

