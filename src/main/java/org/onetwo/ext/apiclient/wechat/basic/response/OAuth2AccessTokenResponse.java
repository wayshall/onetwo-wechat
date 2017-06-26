package org.onetwo.ext.apiclient.wechat.basic.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * snsapi_base
 * 
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class OAuth2AccessTokenResponse extends WechatResponse {
	
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("expires_in")
	private Long expiresIn;
	
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	private String openid;
	private String scope;

}
