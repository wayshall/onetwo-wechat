package org.onetwo.ext.apiclient.yly.core;

import org.onetwo.common.annotation.FieldName;
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
		String id;
	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	public class OAuthResponse extends YlyResponse {
		@JsonProperty("access_token")
		String accessToken;
		@JsonProperty("grant_type")
		String grantType;
		String sign;
		String scope = "all";
		int timestamp;
		String id;
	}
}
