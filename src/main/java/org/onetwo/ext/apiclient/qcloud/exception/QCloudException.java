package org.onetwo.ext.apiclient.qcloud.exception;

import org.onetwo.common.exception.BaseException;
import org.onetwo.common.exception.ErrorType;

/**
 * @author weishao zeng
 * <br/>
 */
@SuppressWarnings("serial")
public class QCloudException extends BaseException {

	public QCloudException(String msg) {
		super(msg);
	}

	public QCloudException(ErrorType exceptionType) {
		super(exceptionType);
	}

	public QCloudException(ErrorType exceptionType, Throwable cause) {
		super(exceptionType, cause);
	}
}

