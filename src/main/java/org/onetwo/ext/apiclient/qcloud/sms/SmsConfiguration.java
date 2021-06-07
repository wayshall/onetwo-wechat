package org.onetwo.ext.apiclient.qcloud.sms;

import org.onetwo.ext.apiclient.qcloud.QCloudProperties;
import org.onetwo.ext.apiclient.qcloud.sms.service.SmsService;
import org.onetwo.ext.apiclient.qcloud.sms.service.impl.QCloudSmsService;
import org.onetwo.ext.apiclient.qcloud.sms.service.impl.RetryableSmsService;
import org.onetwo.ext.apiclient.qcloud.sms.service.impl.TencentSdkSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
@EnableConfigurationProperties({QCloudProperties.class, SmsProperties.class})
public class SmsConfiguration {
	
	@Bean
	@ConditionalOnProperty(name=SmsProperties.ENABLE_KEY, matchIfMissing=false)
	public SmsService qcloudSmsService(SmsProperties smsProperties) {
		SmsService smsService = new QCloudSmsService(smsProperties);
		return smsService;
	}
	
	@Bean
	@ConditionalOnProperty(name= {QCloudProperties.ENABLE_KEY}, matchIfMissing=false)
	@ConditionalOnMissingBean(SmsService.class)
	public SmsService tencentSdkSmsService(SmsProperties smsProperties) {
		SmsService smsService = new TencentSdkSmsService(smsProperties);
		return smsService;
	}
	
	@Bean
	@Primary
	@ConditionalOnClass(RetryConfiguration.class)
	@ConditionalOnProperty(name=SmsProperties.RETRYABLE_ENABLE, havingValue="true", matchIfMissing=true)
	public SmsService retryableSmsService(SmsService smsService) {
		RetryableSmsService retryable = new RetryableSmsService(smsService);
		return retryable;
	}

}

