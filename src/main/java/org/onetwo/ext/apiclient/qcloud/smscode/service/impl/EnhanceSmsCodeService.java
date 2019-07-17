package org.onetwo.ext.apiclient.qcloud.smscode.service.impl;

import java.util.Arrays;

import org.apache.commons.lang3.RandomStringUtils;
import org.onetwo.boot.module.redis.TokenValidator;
import org.onetwo.common.exception.ServiceException;
import org.onetwo.ext.apiclient.qcloud.sms.service.SmsService;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.onetwo.ext.apiclient.qcloud.smscode.service.SmsCodeExceptionTranslator;
import org.onetwo.ext.apiclient.qcloud.smscode.vo.SmsCodeBaseRequest;
import org.onetwo.ext.apiclient.qcloud.smscode.vo.SmsCodeCheckRequest;
import org.onetwo.ext.apiclient.qcloud.smscode.vo.SmsCodeRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class EnhanceSmsCodeService {

	@Autowired
	private SmsService smsService;
	@Autowired
	private TokenValidator tokenValidator;
	
	private SmsCodeExceptionTranslator exceptionTranslator;
	
	public EnhanceSmsCodeService(SmsCodeExceptionTranslator exceptionTranslator) {
		super();
		this.exceptionTranslator = exceptionTranslator;
	}

	public void check(SmsCodeCheckRequest request) {
		try {
			tokenValidator.check(getStoreKey(request), request.getCode(), () -> true, true);
		} catch (ServiceException e) {
			throw translateServiceException(e);
		}
	}
	
	public ServiceException translateServiceException(ServiceException e) {
		return exceptionTranslator.translateServiceException(e);
	}
	
	
	public String obtain(SmsCodeRequest request) {
		String code = null;
		try {
			code = tokenValidator.save(getStoreKey(request), request.getValidInMinutes()*60, () -> {
				return RandomStringUtils.randomNumeric(request.getCodeLength());
			});
		} catch (ServiceException e) {
			throw translateServiceException(e);
		}
		SendSmsRequest smsSendRequest = SendSmsRequest.builder()
											.phoneNumber(request.getMobile())
											.templId(request.getTemplateId())
											.params(Arrays.asList(code, request.getValidInMinutes()+""))
											.sign(request.getSign())
											.build();
		this.smsService.sendTemplateMessage(smsSendRequest);
		return code;
	}

	private String getStoreKey(SmsCodeBaseRequest request) {
		return "EnhanceSmsCode:" + request.getModule() + ":" + request.getMobile();
	}
	
}

