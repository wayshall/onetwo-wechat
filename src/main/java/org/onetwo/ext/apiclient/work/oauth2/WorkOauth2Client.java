package org.onetwo.ext.apiclient.work.oauth2;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档： https://work.weixin.qq.com/api/doc#90000/90135/91023
 * 
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient
public interface WorkOauth2Client {
	
	@GetMapping(path="/user/getuserinfo")
	UserInfoResponse getUserInfo(AccessTokenInfo accessToken, String code);
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public class UserInfoResponse extends WechatResponse implements OAuth2User {
		@JsonProperty("UserId")
		private String userId;
		@JsonProperty("DeviceId")
		private String deviceId;
	}

}

