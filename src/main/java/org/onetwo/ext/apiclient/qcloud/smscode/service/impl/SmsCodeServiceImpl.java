package org.onetwo.ext.apiclient.qcloud.smscode.service.impl;

import java.util.Arrays;
import java.util.function.Supplier;

import org.apache.commons.lang3.RandomStringUtils;
import org.onetwo.boot.module.redis.TokenValidator;
import org.onetwo.boot.module.redis.TokenValidatorErrors;
import org.onetwo.common.exception.ErrorType;
import org.onetwo.common.exception.ServiceException;
import org.onetwo.ext.apiclient.qcloud.sms.service.SmsService;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.onetwo.ext.apiclient.qcloud.smscode.SmsCodeModule;
import org.onetwo.ext.apiclient.qcloud.smscode.SmsCodeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author weishao zeng
 * <br/>
 */
@Service
public class SmsCodeServiceImpl {

	@Autowired
	private SmsService smsService;
	@Autowired
	private TokenValidator tokenValidator;
	private SmsCodeProperties smsCodeProperties;
	
	public SmsCodeServiceImpl(SmsCodeProperties smsCodeProperties) {
		super();
		this.smsCodeProperties = smsCodeProperties;
	}

	/***
	 * 检查验证码
	 * @author weishao zeng
	 * @param mobile
	 * @param code
	 * @param supplier
	 */
	public <T> T check(String mobile, SmsCodeModule bizType, String code, Supplier<T> supplier) {
		try {
			return tokenValidator.check(getStoreKey(mobile, bizType), code, supplier);
		} catch (ServiceException e) {
			throw translateServiceException(e);
		}
	}
	
	public ServiceException translateServiceException(ServiceException e) {
		ErrorType errorType = e.getExceptionType();
		ServiceException newSe = e;
		if (TokenValidatorErrors.TOKEN_INVALID.equals(errorType)) {
			newSe = new ServiceException("验证码错误", e);
		} else if (TokenValidatorErrors.TOKEN_NOT_EXPIRED.equals(errorType)) {
			newSe = new ServiceException("验证码未过期，不能重复发送", e);
		} else if (TokenValidatorErrors.REQUIRED_VALUE.equals(errorType)) {
			throw new ServiceException("缺少参数", e);
		} else {
			newSe = e;
		}
		return newSe;
	}
	
	
	/***
	 * 获取验证码
	 * @author weishao zeng
	 * @param mobile
	 * @param bizType
	 * @return
	 */
	public String obtain(String mobile, SmsCodeModule bizType) {
		String code = null;
		try {
			code = tokenValidator.save(getStoreKey(mobile, bizType), smsCodeProperties.getValidInMinutes()*60, () -> {
				return RandomStringUtils.randomNumeric(smsCodeProperties.getCodeLength());
			});
		} catch (ServiceException e) {
			throw translateServiceException(e);
		}
		SendSmsRequest request = SendSmsRequest.builder()
											.phoneNumber(mobile)
											.templId(smsCodeProperties.getTemplateId())
											.params(Arrays.asList(code, smsCodeProperties.getValidInMinutes()+""))
											.sign(smsCodeProperties.getSign())
											.build();
		this.smsService.sendTemplateMessage(request);
		return code;
	}

	private String getStoreKey(String id, SmsCodeModule bizType) {
		Assert.hasText(id, "id can not be blank");
		Assert.notNull(bizType, "bizType can not be null");
		return smsCodeProperties.getStoreKey() + ":" + bizType.getModuleCode() + ":" + id;
	}
	
}

