package org.onetwo.ext.apiclient.wechat.basic.api;

import org.onetwo.ext.apiclient.wechat.basic.request.AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.request.BaseRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.basic.response.GetCallbackIpResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wayshall
 * <br/>
 */
@WechatApiClient
public interface WechatServer {
	
	@RequestMapping(method=RequestMethod.GET, path="getcallbackip")
	GetCallbackIpResponse getCallbackIp(BaseRequest request);

	@RequestMapping(method=RequestMethod.GET, path="token")
	AccessTokenResponse getAccessToken(AccessTokenRequest request);
	
}
