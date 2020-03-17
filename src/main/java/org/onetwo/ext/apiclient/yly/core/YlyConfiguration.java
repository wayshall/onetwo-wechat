package org.onetwo.ext.apiclient.yly.core;

import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
@EnableConfigurationProperties(YlyAppConfig.class)
public class YlyConfiguration {
	
	@Bean
	public AccessTokenProvider ylyAccessTokenProvider() {
		YlyAccessTokenProvider provider = new YlyAccessTokenProvider();
		return provider;
	}

}

