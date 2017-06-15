package org.onetwo.ext.apiclient.wechat.basic.api;

import org.onetwo.ext.apiclient.wechat.basic.request.Oauth2AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.Oauth2AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842">view api doc</a>
 * 
 * @author wayshall
 * <br/>
 */
@WechatApiClient
public interface WechatOauth2Client extends WechatOauth2Custom {
	
	/****
	 * 第二步：通过code换取网页授权access_token
	 * 
	 * @author wayshall
	 * @param request
	 * @return
	 */
	@GetMapping(value="/sns/oauth2/access_token")
	Oauth2AccessTokenResponse getAccessToken(Oauth2AccessTokenRequest request);
	
	void refreshToken();

}
