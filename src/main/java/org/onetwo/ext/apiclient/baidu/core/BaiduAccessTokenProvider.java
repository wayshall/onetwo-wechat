package org.onetwo.ext.apiclient.baidu.core;

import java.util.Arrays;
import java.util.List;

import org.onetwo.ext.apiclient.baidu.request.BaiduOAuthRequest;
import org.onetwo.ext.apiclient.baidu.response.BaiduOAuthResponse;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenType;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class BaiduAccessTokenProvider implements AccessTokenProvider {
	
	@Autowired
	private BaiduOAuthClient oauthClient;
	@Autowired
	private BaiduAppConfig appConfig;

	@Override
	public AccessTokenResponse getAccessToken(AppidRequest request) {
		WechatConfig appconfig = appConfig.getAppConfig(request.getAppid());
		
		BaiduOAuthRequest oauthRequest = new BaiduOAuthRequest();
		oauthRequest.setClientId(request.getAppid());
		oauthRequest.setGrantType("client_credentials");
		oauthRequest.setClientSecret(appconfig.getAppsecret());
		
		BaiduOAuthResponse oahtResponse = oauthClient.getAccessToken(oauthRequest);
		
		AccessTokenResponse res = new AccessTokenResponse();
		res.setAccessToken(oahtResponse.getAccessToken());
		res.setExpiresIn(oahtResponse.getExpiresIn());
		return res;
	}

	@Override
	public List<AccessTokenType> getAccessTokenTypes() {
		return Arrays.asList(BaiduAccessTokenTypes.BAIDU_BCE);
	}
	
	static public enum BaiduAccessTokenTypes implements AccessTokenType {
		BAIDU_BCE
	}
}

