package org.onetwo.ext.apiclient.wechat.basic.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author wayshall
 * <br/>
 */
@EqualsAndHashCode(callSuper=false)
@Data
@ToString
public class AccessTokenResponse extends WechatResponse {
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("expires_in")
	private int expiresIn;
	

	private Date expireAt;
	

	public AccessTokenResponse() {
		super();
	}

	public AccessTokenResponse(String accessToken, int expiresIn) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

}
