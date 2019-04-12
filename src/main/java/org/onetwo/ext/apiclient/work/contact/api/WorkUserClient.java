package org.onetwo.ext.apiclient.work.contact.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.work.contact.response.WorkUserInfoResponse;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient
public interface WorkUserClient {

	@GetMapping(path="/user/get")
	WorkUserInfoResponse getUser(AccessTokenInfo accessTokenInfo, String userid);
	
	
}

