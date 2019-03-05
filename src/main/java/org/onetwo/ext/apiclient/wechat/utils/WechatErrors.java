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
	ACCESS_TOKEN_MISSING(41001, "access_token missing"),
	
	ACCESS_TOKEN_INVALID_CREDENTIAL(40001, "invalid credential, access_token is invalid or not latest"),
	ACCESS_TOKEN_EXPIRED(42001, "access_token 超时，请检查 access_token 的有效期"),
	ACCESS_TOKEN_INVALID(40014, "不合法的 access_token ，请开发者认真比对 access_token 的有效性（如是否过期）"),
	
	API_UNAUTHORIZED(48001, "api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限"),
	FORMAT_PARSE_ERROR(47001, "解析JSON/XML内容错误"),
	USER_NOT_EXIST(46004, "不存在的用户"),
	WXAPP_LOGIN_ERROR(40029, "微信小程序登录错误，无效的code！"),
	
	CONTENT_RISKY(87014, "内容含有违法违规内容"),

	TEMPLATE_ID_ERROR(40037, "template_id不正确"),
	TEMPLATE_ID_EXPIRED(41028, "form_id不正确，或者过期"),
	TEMPLATE_ID_HAS_USED(41029, "form_id已被使用"),
	PAGE_ERROR(41030, "page不正确"),
	API_CALL_EXCEEDS_QUOTA(45009, "接口调用超过限额（目前默认每个帐号日调用限额为100万）")
	;

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
	public static boolean isNeedToRemoveToken(String errorCode) {
		if (ACCESS_TOKEN_INVALID_CREDENTIAL.getErrorCode().equals(errorCode) || 
				ACCESS_TOKEN_EXPIRED.getErrorCode().equals(errorCode) || 
				ACCESS_TOKEN_INVALID.getErrorCode().equals(errorCode) ) {
			return true;
		}
		return false;
	}
}
