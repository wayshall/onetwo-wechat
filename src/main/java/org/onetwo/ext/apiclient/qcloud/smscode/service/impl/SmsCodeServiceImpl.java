package org.onetwo.ext.apiclient.qcloud.smscode.service.impl;

import java.util.Arrays;
import java.util.function.Supplier;

import org.apache.commons.lang3.RandomStringUtils;
import org.onetwo.boot.module.redis.TokenValidator;
import org.onetwo.common.exception.ServiceException;
import org.onetwo.ext.apiclient.qcloud.sms.service.SmsService;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.onetwo.ext.apiclient.qcloud.smscode.SmsCodeModule;
import org.onetwo.ext.apiclient.qcloud.smscode.SmsCodeProperties;
import org.onetwo.ext.apiclient.qcloud.smscode.service.SmsCodeExceptionTranslator;
import org.onetwo.ext.apiclient.qcloud.smscode.service.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author weishao zeng
 * <br/>
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {

	@Autowired
	private SmsService smsService;
	@Autowired
	private TokenValidator tokenValidator;
	private SmsCodeProperties smsCodeProperties;
	
	private SmsCodeExceptionTranslator exceptionTranslator;
	
	public SmsCodeServiceImpl(SmsCodeProperties smsCodeProperties, SmsCodeExceptionTranslator exceptionTranslator) {
		super();
		this.smsCodeProperties = smsCodeProperties;
		this.exceptionTranslator = exceptionTranslator;
	}

	/* (non-Javadoc)
	 * @see org.onetwo.ext.apiclient.qcloud.smscode.service.impl.SmsCodeService#check(java.lang.String, org.onetwo.ext.apiclient.qcloud.smscode.SmsCodeModule, java.lang.String, java.util.function.Supplier)
	 */
	@Override
	public <T> T check(String mobile, SmsCodeModule bizType, String code, Supplier<T> supplier) {
		try {
			return tokenValidator.check(getStoreKey(mobile, bizType), code, supplier);
		} catch (ServiceException e) {
			throw translateServiceException(e);
		}
	}
	
	public ServiceException translateServiceException(ServiceException e) {
		return exceptionTranslator.translateServiceException(e);
	}
	
	
	/* (non-Javadoc)
	 * @see org.onetwo.ext.apiclient.qcloud.smscode.service.impl.SmsCodeService#obtain(java.lang.String, org.onetwo.ext.apiclient.qcloud.smscode.SmsCodeModule)
	 */
	@Override
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

