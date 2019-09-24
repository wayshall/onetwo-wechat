package org.onetwo.ext.apiclient.wechat.user.api;

import java.util.List;

import javax.validation.Valid;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.user.request.UserInfoGetRequest;
import org.onetwo.ext.apiclient.wechat.user.response.UserInfoGetResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

/**
 * 
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839
 * 
 * @author wayshall
 * <br/>
 */
@WechatApiClient(path="/user/info")
public interface UserInfoClient {
	
	@RequestMapping(method=RequestMethod.GET)
	UserInfoGetResponse get(AccessTokenInfo accessTokenInfo, @Valid UserInfoGetRequest request);

	@PostMapping(path="batchget", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	UserInfoBatchGetResponse batchget(AccessTokenInfo accessTokenInfo, @Valid @RequestBody UserInfoBatchGetRequest request);
	
	@Data
	public static class UserInfoBatchGetRequest {
		@JsonProperty("user_list")
		@Valid
		List<UserInfoGetRequest> userList;
		
		public UserInfoBatchGetRequest add(UserInfoGetRequest getRequest) {
			if(this.userList==null){
				this.userList = Lists.newArrayList();
			}
			this.userList.add(getRequest);
			return this;
		}
	}
	@Data
	@EqualsAndHashCode(callSuper=true)
	public static class UserInfoBatchGetResponse extends WechatResponse {
		@JsonProperty("user_info_list")
		List<UserInfoGetResponse> userInfoList;
	}
}
