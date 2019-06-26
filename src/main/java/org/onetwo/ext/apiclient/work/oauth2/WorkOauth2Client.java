package org.onetwo.ext.apiclient.work.oauth2;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
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
	
	/***
	 * 
	 * @author weishao zeng
	 * @param accessToken
	 * @param code 通过成员授权获取到的code，最大为512字节。每次成员授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
	 * @return
	 */
	@GetMapping(path="/user/getuserinfo")
	UserInfoResponse getUserInfo(AccessTokenInfo accessToken, String code);
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public class UserInfoResponse extends WechatResponse {
		@JsonProperty("UserId")
		private String userId;
		@JsonProperty("DeviceId")
		private String deviceId;
		@JsonProperty("OpenId")
		private String OopenId;
	}

}

