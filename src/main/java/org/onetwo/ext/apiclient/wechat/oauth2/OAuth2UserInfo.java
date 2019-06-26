package org.onetwo.ext.apiclient.wechat.oauth2;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UserInfo implements Serializable, OAuth2User {
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
	

	private Long accessAt;
	private Long refreshAt;

	@JsonIgnore
	public boolean isAccessTokenExpired(){
		Assert.notNull(accessAt, "accessAt can not be null");
		long duration = System.currentTimeMillis() - accessAt;
		return TimeUnit.MILLISECONDS.toSeconds(duration) > expiresIn;
	}
	
	@JsonIgnore
	public boolean isRefreshToken(){
		if(refreshAt==null){
			return false;
		}
		long duration = System.currentTimeMillis() - refreshAt;
		return TimeUnit.MILLISECONDS.toDays(duration) > 30;
	}
	
}
