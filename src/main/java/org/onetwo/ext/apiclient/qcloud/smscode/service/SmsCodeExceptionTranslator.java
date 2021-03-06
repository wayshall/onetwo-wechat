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
			if (TokenValidatorErrors.TOKEN_INVALID_OR_EXPIRED.equals(errorType)) {
				newSe = new ServiceException("验证码错误或已过期", e, errorType.getErrorCode());
				newSe.putAsMap(e.getErrorContext());
			} else if (TokenValidatorErrors.TOKEN_NOT_EXPIRED.equals(errorType)) {
				newSe = new ServiceException("验证码未过期，不能重复发送", e, errorType.getErrorCode());
				newSe.putAsMap(e.getErrorContext());
			} else if (TokenValidatorErrors.REQUIRED_VALUE.equals(errorType)) {
				newSe = new ServiceException("缺少参数", e, errorType.getErrorCode());
				newSe.putAsMap(e.getErrorContext());
			} else {
				newSe = e;
			}
			return newSe;
		}
		
	}
}

