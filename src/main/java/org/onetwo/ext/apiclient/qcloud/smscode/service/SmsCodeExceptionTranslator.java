package org.onetwo.ext.apiclient.qcloud.smscode.service;

import org.onetwo.boot.module.redis.TokenValidatorErrors;
import org.onetwo.common.exception.ErrorType;
import org.onetwo.common.exception.ServiceException;

/**
 * @author weishao zeng
 * <br/>
 */
public interface SmsCodeExceptionTranslator {
	
	ServiceException translateServiceException(ServiceException e);
	
	class DefaultSmsCodeExceptionTranslator implements SmsCodeExceptionTranslator {

		@Override
		public ServiceException translateServiceException(ServiceException e) {
			ErrorType errorType = e.getExceptionType();
			ServiceException newSe = e;
			if (TokenValidatorErrors.TOKEN_INVALID.equals(errorType)) {
				newSe = new ServiceException("验证码错误", e);
			} else if (TokenValidatorErrors.TOKEN_EXPIRED.equals(errorType)) {
				newSe = new ServiceException("验证码已过期", e);
				newSe.putAsMap(e.getErrorContext());
			} else if (TokenValidatorErrors.TOKEN_NOT_EXPIRED.equals(errorType)) {
				newSe = new ServiceException("验证码未过期，不能重复发送", e);
			} else if (TokenValidatorErrors.REQUIRED_VALUE.equals(errorType)) {
				throw new ServiceException("缺少参数", e);
			} else {
				newSe = e;
			}
			return newSe;
		}
		
	}
}

