package org.onetwo.ext.apiclient.qcloud.sms;

import org.onetwo.common.exception.ErrorType;
import org.onetwo.common.exception.ServiceException;

@SuppressWarnings("serial")
public class SmsException extends ServiceException {

	public SmsException(String message) {
		super(message);
	}

	public SmsException(String msg, String code) {
		super(msg, code);
	}

	public SmsException(ErrorType exceptionType) {
		super(exceptionType.getErrorMessage(), exceptionType.getErrorCode());
	}
	
	public SmsException(ErrorType exceptionType, Throwable cause) {
		super(exceptionType, cause);
	}
}
