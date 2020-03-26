package org.onetwo.ext.apiclient.yly.core;

import org.onetwo.common.annotation.FieldName;
import org.onetwo.ext.apiclient.yly.request.YlyRequest;
import org.onetwo.ext.apiclient.yly.response.YlyResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@YlyApiClient
public interface YlyOAuthClient {
	
	/***
	 * http://doc2.10ss.net/371770#Access_Token_6
	 * 
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/oauth/oauth", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	OAuthResponse getAccessToken(OAuthRequest request);
	
	@Data
	@EqualsAndHashCode(callSuper = false)
	public class OAuthRequest extends YlyRequest {
		@FieldName("grant_type")
		String grantType;
		String scope = "all";
	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	public class OAuthResponse extends YlyResponse {
		private OAuthResult body;
	}


	@Data
	@EqualsAndHashCode(callSuper = false)
	public class OAuthResult extends YlyResponse {
		@JsonProperty("access_token")
		String accessToken;

		@JsonProperty("refresh_token")
		String refreshToken;
		
		@JsonProperty("machine_code")
		String machine_code;
		
		@JsonProperty("expires_in")
		int expiresIn;
		String scope;
	}
}
