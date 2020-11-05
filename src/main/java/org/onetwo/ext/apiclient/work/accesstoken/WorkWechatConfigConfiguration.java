package org.onetwo.ext.apiclient.work.accesstoken;

import java.util.List;

import org.onetwo.ext.apiclient.wechat.accesstoken.MultiAppConfig;
import org.onetwo.ext.apiclient.wechat.core.DefaultWechatConfig;
import org.onetwo.ext.apiclient.wechat.event.WechatEventBus;
import org.onetwo.ext.apiclient.work.core.WorkConfigProvider;
import org.onetwo.ext.apiclient.work.core.WorkWechatConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
@EnableConfigurationProperties({DefaultWechatConfig.class, WorkWechatConfig.class})
@ConditionalOnMissingBean(WorkConfigProvider.class)
@ConditionalOnProperty(value = WorkWechatConfig.ENABLED_KEY, matchIfMissing = true)
public class WorkWechatConfigConfiguration {

	@Autowired
	private DefaultWechatConfig wechatConfig;
	@Autowired
	private WorkWechatConfig workWechatConfig;
	@Autowired(required=false)
	List<MultiAppConfig> appConfigs;
	
	/*@Bean
	@Primary
	public WechatConfig wechatConfig(){
		return wechatConfig;
	}*/
	
	@Bean
	@ConditionalOnMissingBean(WorkConfigProvider.class)
	public WorkConfigProvider workWechatConfigProvider(){
		WorkWechatConfigProvider provider = new WorkWechatConfigProvider(wechatConfig, workWechatConfig, appConfigs);
		return provider;
	}
	
	@Bean
	public WechatEventBus wechatEventBus() {
		return new WechatEventBus();
	}
}

