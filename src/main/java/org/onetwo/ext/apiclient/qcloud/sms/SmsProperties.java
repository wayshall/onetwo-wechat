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
	
	private int appId;
	private String appKey;

}

