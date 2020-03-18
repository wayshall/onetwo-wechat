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
	public static final String ENABLE_KEY = PREFIX + ".enabled";
	
	/***
	 * 配置 qcloud.smsCode.template-id 启动
	 */
	public static final String SMS_SERVICE_ENABLE_KEY = PREFIX + ".template-id";

	private String storeKey = "smsCode";
	private int codeLength = 4;
	private int validInMinutes = 2;
	private int templateId;
	private String sign;

}

