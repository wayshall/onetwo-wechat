package org.onetwo.ext.apiclient.qcloud.smscode;

import org.onetwo.ext.apiclient.qcloud.smscode.service.SmsCodeExceptionTranslator;
import org.onetwo.ext.apiclient.qcloud.smscode.service.SmsCodeExceptionTranslator.DefaultSmsCodeExceptionTranslator;
import org.onetwo.ext.apiclient.qcloud.smscode.service.SmsCodeService;
import org.onetwo.ext.apiclient.qcloud.smscode.service.impl.SmsCodeServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
@EnableConfigurationProperties(SmsCodeProperties.class)
@ConditionalOnProperty(name=SmsCodeProperties.ENABLE_KEY, matchIfMissing=true)
public class SmsCodeConfiguration {
	
	@Bean
	public SmsCodeService smsCodeService(SmsCodeProperties smsCodeProperties, SmsCodeExceptionTranslator translator) {
		SmsCodeService smsService = new SmsCodeServiceImpl(smsCodeProperties, translator);
		return smsService;
	}
	
	@Bean
	public SmsCodeExceptionTranslator smsCodeExceptionTranslator() {
		return new DefaultSmsCodeExceptionTranslator();
	}

}

