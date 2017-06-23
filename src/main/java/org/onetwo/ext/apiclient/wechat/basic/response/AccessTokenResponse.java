package org.onetwo.ext.apiclient.wechat.basic.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wayshall
 * <br/>
 */
@EqualsAndHashCode(callSuper=true)
@Data
@ToString
public class AccessTokenResponse extends WechatResponse {
	@JsonProperty("access_token")
	private String accessToken;
	
	@JsonProperty("expires_in")
	private int expiresIn;
}
