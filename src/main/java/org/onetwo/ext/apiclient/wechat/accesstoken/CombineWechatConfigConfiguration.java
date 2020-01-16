package org.onetwo.ext.apiclient.wechat.accesstoken;

import java.util.List;

import org.onetwo.ext.apiclient.wechat.core.DefaultWechatConfig;
import org.onetwo.ext.apiclient.wechat.core.WechatConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
@EnableConfigurationProperties({DefaultWechatConfig.class})
@ConditionalOnMissingBean(WechatConfigProvider.class)
public class CombineWechatConfigConfiguration {

	@Autowired
	private DefaultWechatConfig wechatConfig;
	/*@Autowired
	private WorkWechatConfig workWechatConfig;*/
	@Autowired(required=false)
	List<MultiAppConfig> appConfigs;
	
	/*@Bean
	@Primary
	public WechatConfig wechatConfig(){
		return wechatConfig;
	}*/
	
	@Bean
	public WechatConfigProvider wechatConfigProvider(){
		CombineWechatConfigProvider provider = new CombineWechatConfigProvider(wechatConfig, appConfigs);
		provider.setWechatConfig(wechatConfig);
		return provider;
	}
}

