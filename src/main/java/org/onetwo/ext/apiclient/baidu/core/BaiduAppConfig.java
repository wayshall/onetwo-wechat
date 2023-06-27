package org.onetwo.ext.apiclient.baidu.core;

import org.onetwo.ext.apiclient.wechat.accesstoken.MultiAppConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * baidu: 
 * 	   default: 
 * 			appid: appsecrect
 * @author weishao zeng
 * <br/>
 */
@ConfigurationProperties(BaiduAppConfig.CONFIG_PREFIX)
public class BaiduAppConfig extends MultiAppConfig {
	public static final String CONFIG_PREFIX  = "baidu";
	
}

