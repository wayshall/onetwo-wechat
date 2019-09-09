package org.onetwo.ext.apiclient.qcloud.sms.service;

import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;

/**
 * 错误码：https://cloud.tencent.com/document/product/382/3771
 * 
 * @author weishao zeng
 * <br/>
 */
public interface SmsService {

	void sendTemplateMessage(SendSmsRequest request);

}
