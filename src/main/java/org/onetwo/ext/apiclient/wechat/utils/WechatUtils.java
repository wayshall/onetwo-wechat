package org.onetwo.ext.apiclient.wechat.utils;

import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;

/**
 * @author wayshall
 * <br/>
 */
public class WechatUtils {

	public static final String REDIS_ACCESS_TOKEN_KEY = "wechat_access_token";
	public static final String LOCK_KEY = "lock_wechat_obtainAcessTokenLock";
	
	public static AccessTokenInfo getAccessToken(WechatServer wechatServer, WechatConfig wechatConfig){
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
														.grantType(wechatConfig.getGrantType())
														.appid(wechatConfig.getAppid())
														.secret(wechatConfig.getAppsecret())
														.build();
		
		AccessTokenResponse response = wechatServer.getAccessToken(request);
		AccessTokenInfo accessToken = AccessTokenInfo.builder()
									.accessToken(response.getAccessToken())
									.expiresIn(response.getExpiresIn())
									.build();
		return accessToken;
	}
	
	private WechatUtils(){
	}

}
