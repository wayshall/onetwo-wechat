package org.onetwo.ext.apiclient.wechat.utils;

import java.lang.reflect.Method;

import org.onetwo.common.exception.BaseException;
import org.onetwo.common.exception.ErrorType;
import org.onetwo.common.exception.ExceptionCodeMark;
import org.onetwo.common.utils.StringUtils;

/*********
 * 
 * @author wayshall
 *
 */
@SuppressWarnings("serial")
public class WechatException extends BaseException implements ExceptionCodeMark{
	public static final String BASE_CODE = "[Wechat]";//前缀

	protected String code;
	private Object[] args;

	public WechatException(ErrorType exceptionType) {
		this(exceptionType, (Throwable)null);
	}

	public WechatException(ErrorType exceptionType, Method method) {
		super(String.format(exceptionType.getErrorMessage(), method.toGenericString()));
		initErrorCode(exceptionType.getErrorCode());
	}

	public WechatException(ErrorType exceptionType, Throwable cause) {
		super(exceptionType.getErrorMessage(), cause);
		initErrorCode(exceptionType.getErrorCode());
	}

	public WechatException(String message) {
		super(message);
	}
	
	public WechatException(String message, Throwable cause) {
		super(message, cause);
		initErrorCode(BASE_CODE);
	}
	
	final protected void initErrorCode(String code){
		if(StringUtils.isNotBlank(code))
			this.code = code;//appendBaseCode(code);
	}
	public String getCode() {
		if(StringUtils.isBlank(code))
			return BASE_CODE;
		return code;
	}
	
	public Object[] getArgs() {
		return args;
	}
	
	@Override
	public String toString() {
		return "ApiClientException [" + code + ", " + getMessage() +  "]";
	}

}
