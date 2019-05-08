package org.onetwo.ext.apiclient.work.core;

import java.util.Map;

import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.core.DefaultWechatConfig;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@ConfigurationProperties(WorkWechatConfig.CONFIG_PREFIX)
@Data
public class WorkWechatConfig /*extends DefaultWechatConfig*/ {

	public static final String CONFIG_PREFIX  = "work-wechat";
	
	private Map<String, DefaultWechatConfig> apps = Maps.newLinkedHashMap();
	
	public WechatConfig getDefaultWechatConfig() {
		return LangUtils.getFirst(apps);
	}
	
	/****
	 * 首先根据appid匹配配置，如果找不到，则把appid参数当做名称查找
	 * 
	 * @author weishao zeng
	 * @param appid
	 * @return
	 */
	public WechatConfig getWechatConfig(String appid) {
		return this.apps.entrySet().stream().filter(entry -> {
			return entry.getValue().getAppid().equals(appid);
		})
		.findFirst()
		.map(entry -> (WechatConfig)entry.getValue())
//		.orElse(null);
		.orElseGet(() -> this.getWechatConfigByName(appid));
	}
	
	public WechatConfig getWechatConfigByAgentId(String agentId) {
		return this.apps.entrySet().stream().filter(entry -> {
			return entry.getValue().getAgentId().equals(agentId);
		})
		.findFirst()
		.map(entry -> (WechatConfig)entry.getValue())
		.orElseGet(() -> this.getWechatConfigByName(agentId));
	}
	
	public WechatConfig getWechatConfigByName(String appName) {
		return this.apps.get(appName);
	}
}

