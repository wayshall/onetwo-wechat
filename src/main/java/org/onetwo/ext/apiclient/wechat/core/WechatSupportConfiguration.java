package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.ext.apiclient.wechat.support.BaseSupportService;
import org.onetwo.ext.apiclient.wechat.support.impl.BaseSupportServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
public class WechatSupportConfiguration {

	@Bean
	public BaseSupportService baseSupportService(){
		return new BaseSupportServiceImpl();
	}
	
	@Bean
	public WechatConfig wechatConfig(){
		return new DefaultWechatConfig();
	}
}
