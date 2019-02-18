package org.onetwo.ext.apiclient.qcloud;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@ConfigurationProperties(TestSmsProperties.KEY)
public class TestSmsProperties {
	public static final String KEY = "test.sms";
	
	private String phone;
	private int templateId1;

}

