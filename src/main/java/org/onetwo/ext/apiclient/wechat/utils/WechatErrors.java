package org.onetwo.ext.apiclient.wechat.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.onetwo.common.exception.ErrorType;

import com.google.common.collect.Maps;

/**
 * 错误码查询工具：https://open.work.weixin.qq.com/devtool/query?e=60123
 * 错误码文档：https://qydev.weixin.qq.com/wiki/index.php?title=%E5%85%A8%E5%B1%80%E8%BF%94%E5%9B%9E%E7%A0%81%E8%AF%B4%E6%98%8E
 * 
 * @author wayshall
 * <br/>
 */
public enum WechatErrors implements ErrorType {
	ACCESS_TOKEN_MISSING(41001, "access_token missing"),
	
	ACCESS_TOKEN_INVALID_CREDENTIAL(40001, "invalid credential, access_token is invalid or not latest"),
	ACCESS_TOKEN_EXPIRED(42001, "access_token 超时，请检查 access_token 的有效期"),
	/***
	 * https://open.work.weixin.qq.com/devtool/query?e=301002
	 */
	ACCESS_TOKEN_NOT_ALLOW_AGENT(301002, "not allow operate another agent with this accesstoken"),
	ACCESS_TOKEN_INVALID(40014, "不合法的 access_token ，请开发者认真比对 access_token 的有效性（如是否过期）"),
	
	API_UNAUTHORIZED(48001, "api功能未授权，请确认公众号已获得该接口，可以在公众平台官网-开发者中心页中查看接口权限"),
	FORMAT_PARSE_ERROR(47001, "解析JSON/XML内容错误"),
	USER_NOT_EXIST(46004, "不存在的用户"),
	WXAPP_LOGIN_ERROR(40029, "微信小程序（公众号）登录错误，无效的code！"),
	
	CONTENT_RISKY(87014, "内容含有违法违规内容"),

	TEMPLATE_ID_ERROR(40037, "订阅模板id为空不正确"),
	TEMPLATE_ID_EXPIRED(41028, "form_id不正确，或者过期"),
	TEMPLATE_ID_HAS_USED(41029, "form_id已被使用"),
	PAGE_ERROR(41030, "page路径不正确，需要保证在现网版本小程序中存在，与app.json保持一致"),
	TEMPLATE_USER_REJECT(43101, "用户拒绝接受消息，如果用户之前曾经订阅过，则表示用户取消了订阅关系"),
	TEMPLATE_DATA_INVALID(47003, "模板参数不准确，可能为空或者不满足规则，errmsg会提示具体是哪个字段出错"),
	API_CALL_EXCEEDS_QUOTA(45009, "接口调用超过限额（目前默认每个帐号日调用限额为100万）"),
	

	WORK_USER_ID_INVALID(40003, "无效的UserID"),
	WORK_USER_NOT_FOUND(60111, "用户不存在"),
	WORK_USER_DISABLED(60120, "成员已禁用"),
	WORK_INVALID_PARTY_ID(60123, "无效的部门id"),
	WORK_MISS_PARTY_ID(60127, "缺少部门id"),
	WORK_PARTY_EXCEED_MAX(81004, "部门数量超过上限")
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
	
	/***
	 * 是否api token过期等相关错误
	 * @author weishao zeng
	 * @param errorCode
	 * @return
	 */
	public static boolean isTokenError(String errorCode) {
		if (ACCESS_TOKEN_INVALID_CREDENTIAL.getErrorCode().equals(errorCode) || 
				ACCESS_TOKEN_EXPIRED.getErrorCode().equals(errorCode) || 
				ACCESS_TOKEN_INVALID.getErrorCode().equals(errorCode) ) {
			return true;
		}
		return false;
	}
	
	public static boolean isNeedToRemoveToken(String errorCode) {
		return isTokenError(errorCode);
	}
}
