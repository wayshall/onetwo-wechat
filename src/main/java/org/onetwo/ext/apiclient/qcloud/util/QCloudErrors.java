package org.onetwo.ext.apiclient.qcloud.util;

import org.onetwo.common.exception.ErrorType;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wayshall <br/>
 */
public class QCloudErrors {
	
	@AllArgsConstructor
	static public enum SmsErrors implements ErrorType {
		ERR_MOBILE_LENTH("手机号错误"),
		ERR_SMS_SEND("发送短信错误");
		
		@Getter
		final private String errorMessage;

		@Override
		public String getErrorCode() {
			return name();
		}
		
	}
	
	
	private QCloudErrors() {
	}
}
