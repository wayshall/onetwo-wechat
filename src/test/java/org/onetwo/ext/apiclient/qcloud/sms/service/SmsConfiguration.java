package org.onetwo.ext.apiclient.qcloud.sms.service;

import org.onetwo.ext.apiclient.qcloud.sms.SmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsConfiguration {
	
	@Bean
	public SmsService smsService(SmsProperties smsProperties) {
		SmsService smsService = new SmsService(smsProperties);
		return smsService;
	}

}

