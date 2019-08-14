package org.onetwo.ext.apiclient.wxpay;

import org.onetwo.ext.apiclient.wechat.core.DefaultWechatConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
@EnableConfigurationProperties({DefaultWechatConfig.class})
public class WechatPayConfiguration {
	
}

