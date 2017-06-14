package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.ext.apiclient.wechat.support.impl.AccessTokenServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@ComponentScan(basePackageClasses=AccessTokenServiceImpl.class)
public class WechatSupportConfiguration {

	
	@Bean
	public WechatConfig wechatConfig(){
		return new DefaultWechatConfig();
	}
}
