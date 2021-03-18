package org.onetwo.ext.apiclient.qcloud.sms.service;

import org.junit.Test;
import org.onetwo.ext.apiclient.qcloud.sms.QCloudSmsBaseBootTests;
import org.onetwo.ext.apiclient.qcloud.sms.TestSmsProperties;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

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
//											.phoneNumber(testSmsProperties.getPhone())
											.phoneNumbers(new String[] {testSmsProperties.getPhone()})
											.templId(testSmsProperties.getTemplateId1())
											.params(Lists.newArrayList("123456", "2")) // 注意：验证码只能是数字
											.sign(testSmsProperties.getSign())
											.build();
		this.smsService.sendTemplateMessage(request);
	}

}

