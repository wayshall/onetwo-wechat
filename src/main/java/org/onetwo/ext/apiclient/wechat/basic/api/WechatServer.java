package org.onetwo.ext.apiclient.wechat.basic.api;

import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.request.AccessTokenRequest;
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
	GetCallbackIpResponse getCallbackIp(AccessTokenRequest request);

	/*****
	 * <a href="https://mp.weixin.qq.com/wiki?action=doc&id=mp1421140183">view api doc</a>
	 * @author wayshall
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET, path="token")
	AccessTokenResponse getAccessToken(GetAccessTokenRequest request);
	
}
