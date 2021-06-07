package org.onetwo.ext.apiclient.wechat.accesstoken;

import java.util.Map;

import org.onetwo.ext.apiclient.wechat.core.DefaultWechatConfig;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * 主要为了扩展其它小程序的配置，比如头条小程序
 * toutiao: 
 * 		apps:
 *  		default: 
 *  			appid: xxxx
 * @author weishao zeng
 * <br/>
 */
@Data
public class MultiAppConfig {

	protected Map<String, DefaultWechatConfig> apps = Maps.newLinkedHashMap();
	
	@Deprecated
	public WechatConfig getWechatConfig(String appid) {
		return getAppConfig(appid);
	}
	/****
	 * 首先根据appid匹配配置，如果找不到，则把appid参数当做名称查找
	 * 
	 * @author weishao zeng
	 * @param appid
	 * @return
	 */
	public WechatConfig getAppConfig(String appid) {
		return this.apps.entrySet().stream().filter(entry -> {
			String id = entry.getValue().getAppid();
			return id!=null && id.equals(appid);
		})
		.findFirst()
		.map(entry -> (WechatConfig)entry.getValue())
//		.orElse(null);
		.orElseGet(() -> this.getWechatConfigByName(appid));
	}
	
	
	public WechatConfig getWechatConfigByName(String appName) {
		return this.apps.get(appName);
	}
}

