package org.onetwo.ext.apiclient.yly.core;

import org.onetwo.ext.apiclient.wechat.accesstoken.MultiAppConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author weishao zeng
 * <br/>
 */
@ConfigurationProperties(YlyAppConfig.CONFIG_PREFIX)
public class YlyAppConfig extends MultiAppConfig {
	public static final String CONFIG_PREFIX  = "yilianyun";
	
}

