package org.onetwo.ext.apiclient.wechat.basic.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Builder
public class Oauth2AccessTokenResponse extends WechatResponse {
	
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("expires_in")
	private String expiresIn;
	
	@JsonProperty("refresh_token")
	private String refreshToken;
	
	private String openid;
	private String scope;

}
