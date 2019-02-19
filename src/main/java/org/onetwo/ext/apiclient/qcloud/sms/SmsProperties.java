package org.onetwo.ext.apiclient.qcloud.sms;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@ConfigurationProperties(SmsProperties.KEY)
public class SmsProperties {
	public static final String KEY = "qcloud.sms";
	public static final String ENABLE_KEY = KEY + ".enabled";
	public static final String RETRYABLE_ENABLE = KEY + ".retryable";
	
	private int appId;
	private String appKey;

	/*private String protection;
	private String protectionTime;

	*/
	
	/****
	 * 默认一分钟内不能重发
	 * @author weishao zeng
	 * @return
	 *//*
	public long getProtectionTimeInSeconds() {
		return LangOps.timeToSeconds(protectionTime, 60L);
	}*/
}

