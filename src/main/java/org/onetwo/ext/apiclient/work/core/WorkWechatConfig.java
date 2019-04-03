package org.onetwo.ext.apiclient.work.core;

import java.util.Map;

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
	
	private Map<String, DefaultWechatConfig> apps = Maps.newHashMap();
	
	public WechatConfig getWechatConfig(String appid) {
		return this.apps.entrySet().stream().filter(entry -> {
			return entry.getValue().getAppid().equals(appid);
		})
		.findFirst()
		.map(entry -> entry.getValue())
		.orElse(null);
	}
}

