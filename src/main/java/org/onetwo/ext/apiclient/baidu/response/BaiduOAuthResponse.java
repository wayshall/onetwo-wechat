package org.onetwo.ext.apiclient.baidu.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaiduOAuthResponse  extends BaiduBaseResponse{
	@JsonProperty("access_token")
	String accessToken;

	@JsonProperty("refresh_token")
	String refreshToken;
	
	@JsonProperty("expires_in")
	int expiresIn;

	@JsonProperty("session_key")
	String sessionKey;
	
	String scope;
}
