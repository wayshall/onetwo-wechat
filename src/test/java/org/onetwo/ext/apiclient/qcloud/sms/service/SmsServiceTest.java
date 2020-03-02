package org.onetwo.ext.apiclient.qcloud.sms.service;

import java.util.Arrays;

import org.junit.Test;
import org.onetwo.ext.apiclient.qcloud.sms.QCloudSmsBaseBootTests;
import org.onetwo.ext.apiclient.qcloud.sms.TestSmsProperties;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class SmsServiceTest extends QCloudSmsBaseBootTests {
	@Autowired
	private SmsService smsService;
	@Autowired
	private TestSmsProperties testSmsProperties;
	
	@Test
	public void testSend() {
		SendSmsRequest request = SendSmsRequest.builder()
											.phoneNumber(testSmsProperties.getPhone())
											.templId(testSmsProperties.getTemplateId1())
											.params(Arrays.asList("test1", "2019-92-18"))
//											.sign("wechat")
											.build();
		this.smsService.sendTemplateMessage(request);
	}

}

