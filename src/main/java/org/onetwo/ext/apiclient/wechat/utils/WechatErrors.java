package org.onetwo.ext.apiclient.wechat.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.onetwo.common.exception.ErrorType;

import com.google.common.collect.Maps;

/**
 * @author wayshall
 * <br/>
 */
public enum WechatErrors implements ErrorType {
	API_UNAUTHORIZED(48001, "api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限"),
	FORMAT_PARSE_ERROR(47001, "解析JSON/XML内容错误"),
	USER_NOT_EXIST(46004, "不存在的用户");

	private static final Map<Integer, WechatErrors> ERROR_MAP;
	
	static {
		Map<Integer, WechatErrors> temp = Maps.newHashMapWithExpectedSize(WechatErrors.values().length);
		for(WechatErrors err : WechatErrors.values()){
			temp.put(err.errcode, err);
		}
		ERROR_MAP = Collections.unmodifiableMap(temp);
	}
	
	private Integer errcode;
	private String errmsg;

	private WechatErrors(int errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	@Override
	public String getErrorCode() {
		return errcode.toString();
	}

	@Override
	public String getErrorMessage() {
		return errmsg;
	}
	
	public static Optional<WechatErrors> byErrcode(int errcode){
		return Optional.ofNullable(ERROR_MAP.get(errcode));
	}
}
