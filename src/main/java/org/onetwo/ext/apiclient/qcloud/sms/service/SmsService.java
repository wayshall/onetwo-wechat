package org.onetwo.ext.apiclient.qcloud.sms.service;

import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;

/**
 * @author weishao zeng
 * <br/>
 */
public interface SmsService {

	void sendTemplateMessage(SendSmsRequest request);

}
