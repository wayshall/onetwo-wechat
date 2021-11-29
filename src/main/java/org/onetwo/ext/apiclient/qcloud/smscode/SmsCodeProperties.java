package org.onetwo.ext.apiclient.qcloud.smscode;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@ConfigurationProperties(prefix=SmsCodeProperties.PREFIX)
@Data
public class SmsCodeProperties {
	
	public static final String PREFIX = "qcloud.smsCode";
	/***
	 * 恢复使用 qcloud.smsCode.enabled 来控制是否启用sms相关服务，因为有时候 template-id 并不是静态配置的，不是使用通过此属性来判断是否启用
	 */
	public static final String ENABLE_KEY = PREFIX + ".enabled";
	
	/***
	 * 配置 qcloud.smsCode.template-id 启动
	 * 
	 */
	// @Deprecated 有时候 template-id 并不是静态配置的，不是使用通过此属性来判断是否启用
//	public static final String SMS_SERVICE_ENABLE_KEY = PREFIX + ".template-id";

	private String storeKey = "smsCode";
	private int codeLength = 4;
	private int validInMinutes = 2;
	private int templateId;
	private String sign;

}

