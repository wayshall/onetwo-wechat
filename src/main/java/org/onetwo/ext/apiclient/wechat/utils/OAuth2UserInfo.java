package org.onetwo.ext.apiclient.wechat.utils;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UserInfo implements Serializable {
	/***
	 * 
	 */
	private String accessToken;
	/***
	 * 2个小时
	 */
	@JsonProperty("expires_in")
	private Long expiresIn;
	@JsonProperty("refresh_token")
	private String refreshToken;
	private String openid;
	private String scope;
	

	private String nickname;
	private String sex;
	private String province;
	private String city;
	private String country;
	private String headimgurl;
	private List<String> privilege;
	private String unionid;
	

	private Long accessAt = System.currentTimeMillis();
	private Long refreshAt;
	
	public boolean isAccessTokenExpired(){
		long duration = System.currentTimeMillis() - accessAt;
		return TimeUnit.MILLISECONDS.toSeconds(duration) > expiresIn;
	}
	
}
