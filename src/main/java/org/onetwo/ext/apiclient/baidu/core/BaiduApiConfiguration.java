package org.onetwo.ext.apiclient.baidu.core;

import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
@EnableConfigurationProperties(BaiduAppConfig.class)
public class BaiduApiConfiguration {
	
	public static final String ACCESS_TOKEN_PROVIDER_NAME = "baiduAccessTokenProvider";
	
	@Bean
	@ConditionalOnMissingBean(name = BaiduApiConfiguration.ACCESS_TOKEN_PROVIDER_NAME)
	public AccessTokenProvider baiduAccessTokenProvider() {
		BaiduAccessTokenProvider provider = new BaiduAccessTokenProvider();
		return provider;
	}

}

