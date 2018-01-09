package org.onetwo.ext.apiclient.wechat.basic.api;

import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wayshall
 * <br/>
 */
@WechatApiClient
public interface TokenApi {
	
	@RequestMapping(method=RequestMethod.GET, path="token")
	AccessTokenResponse getAccessToken(GetAccessTokenRequest request);
	
}
