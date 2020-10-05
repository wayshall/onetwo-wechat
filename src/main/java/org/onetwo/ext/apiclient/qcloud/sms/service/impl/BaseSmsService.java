package org.onetwo.ext.apiclient.qcloud.sms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.exception.ServiceException;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.qcloud.sms.SmsProperties;
import org.onetwo.ext.apiclient.qcloud.sms.service.SmsService;
import org.onetwo.ext.apiclient.qcloud.sms.vo.SendSmsRequest;
import org.onetwo.ext.apiclient.qcloud.util.QCloudErrors.SmsErrors;
import org.slf4j.Logger;

import lombok.Getter;

/**
 * @author weishao zeng
 * <br/>
 */
abstract public class BaseSmsService implements SmsService {

	protected final Logger logger = JFishLoggerFactory.getLogger(getClass());
	
	@Getter
	protected SmsProperties smsProperties;

	public BaseSmsService(SmsProperties smsProperties) {
		super();
		this.smsProperties = smsProperties;
	}


	protected void checkRequest(SendSmsRequest request) {
		if (StringUtils.isBlank(request.getPhoneNumber()) || request.getPhoneNumber().length()!=11) {
			throw new ServiceException(SmsErrors.ERR_MOBILE_LENTH);
		}
	}
	
	/****
	 * 若可以发送，则返回true，否则返回false
	 * @author weishao zeng
	 * @param phoneNumber
	 * @return
	 */
	protected boolean checkWhiteBlackList(String phoneNumber) {
		if (LangUtils.isNotEmpty(smsProperties.getWhiteList())) {
			// 白名单不为空时，只有在白名单里的电话号码才允许发送短信
			if (!smsProperties.getWhiteList().contains(phoneNumber)) {
				if (logger.isWarnEnabled()) {
					logger.warn("该电话号码[{}] 不在白名单里，忽略发送短信!", phoneNumber);
				}
				return false;
			}
		}else if (isInBlackList(phoneNumber)) { // 黑名单里的电话号码不允许发送短信
			if (logger.isWarnEnabled()) {
				logger.warn("该电话号码[{}] 在黑单里，忽略发送短信!", phoneNumber);
			}
			return false;
		}
		return true;
	}

	
	private boolean isInBlackList(String phoneNumber) {
		if (LangUtils.isEmpty(smsProperties.getBlackList())) {
			return false;
		}
		return smsProperties.getBlackList().contains(phoneNumber);
	}
}
