package org.onetwo4j.sample.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class AccessTokenResponse {
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("expires_in")
	private int expiresIn;
}
