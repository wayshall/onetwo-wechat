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
		ERR_NOT_SUPPORT_BATCH("不支持批量发送"),
		ERR_NOT_IN_WHITE_BLACK_LIST("手机号码没有配置黑/白名单"),
		ERR_SMS_SEND("发送短信错误"),
		ERR_SMS_TEMPLATE("短信模板错误");
		
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
