package org.onetwo.ext.apiclient.qcloud.sms.service.impl;
/**
 * @author weishao zeng
 * <br/>
 */

import org.onetwo.ext.apiclient.qcloud.sms.service.SmsService;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public class RetryableSmsService implements SmsService {
	
	private SmsService delegator;
	
	public RetryableSmsService(SmsService delegator) {
		super();
		this.delegator = delegator;
	}
	
	/***
	 * multiplier 倍数递增
	 * 0: 0
	 * 1: 3
	 * 2: 3*3
	 * @return
	 */
	@Retryable(maxAttempts=3, backoff=@Backoff(delay=3000, multiplier=3))
	@Override
	public void sendTemplateMessage(SendSmsRequest request) {
		delegator.sendTemplateMessage(request);
	}
	

}

