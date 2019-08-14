package org.onetwo.ext.apiclient.wechat.utils;

import org.onetwo.common.exception.ErrorType;

/**
 * @author wayshall
 * <br/>
 */
public enum WechatClientErrors implements ErrorType {
	ACCESS_TOKEN_REFRESH_ERROR("refresh token error"),
	ACCESS_TOKEN_SERVICE_NOT_FOUND("AccessTokenService not found"),
	ACCESS_TOKEN_OBTAIN_ERROR("获取AccessToken错误！"),
	ACCESS_TOKEN_CANNOT_BE_NULL("缺少AccessToken！"),
	OAUTH2_NOT_AUTHORIZE("请先通过微信授权！"),
	OAUTH2_REJECTED("用户拒绝授权！"),
	OAUTH2_ERROR_IN_BROWSER("请使用微信！"),
	OAUTH2_STATE_ERROR("state参数错误"),
	OAUTH2_REDIRECT_URL_BLANK("redirect url must not be blank"),
	

//	WORK_GET_USERINFO_ERROR("获取企业微信用户信息失败"),
	OAUTH2_USER_NOT_WORK_MEMBER("用户不是企业微信成员");
	
	private String errorMessage;

	private WechatClientErrors(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String getErrorCode() {
		return name();
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}
	
}
