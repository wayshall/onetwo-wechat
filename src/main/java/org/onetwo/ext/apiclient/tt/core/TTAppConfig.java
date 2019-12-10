package org.onetwo.ext.apiclient.tt.core;

import org.onetwo.ext.apiclient.wechat.accesstoken.MultiAppConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author weishao zeng
 * <br/>
 */
@ConfigurationProperties(TTAppConfig.CONFIG_PREFIX)
public class TTAppConfig extends MultiAppConfig {
	public static final String CONFIG_PREFIX  = "toutiao";

}

