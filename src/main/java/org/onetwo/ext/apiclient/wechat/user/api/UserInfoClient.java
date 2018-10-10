package org.onetwo.ext.apiclient.wechat.user.api;

import java.util.List;

import javax.validation.Valid;

import lombok.Data;

import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.user.request.UserInfoGetRequest;
import org.onetwo.ext.apiclient.wechat.user.response.UserInfoGetResponse;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonProperty;

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

	@RequestMapping(method=RequestMethod.GET)
	UserInfoBatchGetResponse batchget(AccessTokenInfo accessTokenInfo, @Valid UserInfoBatchGetRequest request);
	
	@Data
	public static class UserInfoBatchGetRequest {
		@JsonProperty("user_list")
		List<UserInfoGetRequest> userList;
	}
	@Data
	public static class UserInfoBatchGetResponse {
		@JsonProperty("user_info_list")
		List<UserInfoGetResponse> userInfoList;
	}
}
