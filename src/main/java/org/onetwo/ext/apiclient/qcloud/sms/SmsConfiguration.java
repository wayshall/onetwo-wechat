package org.onetwo.ext.apiclient.qcloud.sms;

import org.onetwo.ext.apiclient.qcloud.sms.service.SmsService;
import org.onetwo.ext.apiclient.qcloud.sms.service.impl.QCloudSmsService;
import org.onetwo.ext.apiclient.qcloud.sms.service.impl.RetryableSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.annotation.RetryConfiguration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
@EnableConfigurationProperties(SmsProperties.class)
@ConditionalOnProperty(name=SmsProperties.ENABLE_KEY, matchIfMissing=true)
public class SmsConfiguration {
	
	@Bean
	public SmsService smsService(SmsProperties smsProperties) {
		SmsService smsService = new QCloudSmsService(smsProperties);
		return smsService;
	}
	
	@Bean
	@Primary
	@ConditionalOnClass(RetryConfiguration.class)
	@ConditionalOnProperty(name=SmsProperties.RETRYABLE_ENABLE, havingValue="true", matchIfMissing=true)
	public SmsService retryableSmsService(SmsProperties smsProperties) {
		RetryableSmsService retryable = new RetryableSmsService(smsService(smsProperties));
		return retryable;
	}

}

