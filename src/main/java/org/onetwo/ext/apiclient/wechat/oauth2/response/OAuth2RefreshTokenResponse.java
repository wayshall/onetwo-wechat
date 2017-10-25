package org.onetwo.ext.apiclient.wechat.oauth2.response;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OAuth2RefreshTokenResponse extends WechatResponse {

	/***
	 * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	 */
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("expires_in")
	private String expiresIn;
	
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	private String openid;
	
	private String scope;

}
