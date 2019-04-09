package org.onetwo.ext.apiclient.work.user.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.onetwo.ext.apiclient.work.user.response.WorkUserInfoResponse;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient
public interface WorkUserClient {

	@GetMapping(path="/user/get")
	WorkUserInfoResponse get(AccessTokenInfo accessTokenInfo, String userid);
	
	
}

