package org.onetwo.ext.apiclient.yly.core;

import java.util.Arrays;
import java.util.List;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenType;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.yly.core.YlyOAuthClient.OAuthRequest;
import org.onetwo.ext.apiclient.yly.core.YlyOAuthClient.OAuthResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class YlyAccessTokenProvider implements AccessTokenProvider {
	
	@Autowired
	private YlyOAuthClient ylyOAuthClient;
	@Autowired
	private YlyAppConfig appConfig;

	@Override
	public AccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
		WechatConfig appconfig = appConfig.getAppConfig(request.getAppid());
		
		OAuthRequest oauthRequest = new OAuthRequest();
		oauthRequest.setClientId(request.getAppid());
		oauthRequest.setGrantType("client_credentials");
//		oauthRequest.setId(UUID.randomUUID().toString());
//		oauthRequest.setTimestamp((int)(System.currentTimeMillis()/1000));
		
		oauthRequest.sign(appconfig.getAppsecret());
		
		OAuthResult oahtResponse = ylyOAuthClient.getAccessToken(oauthRequest).getBody();
		
		AccessTokenResponse res = new AccessTokenResponse();
		res.setAccessToken(oahtResponse.getAccessToken());
		res.setExpiresIn(oahtResponse.getExpiresIn());
		return res;
	}

	@Override
	public List<AccessTokenType> getAccessTokenTypes() {
		return Arrays.asList(YlyAccessTokenTypes.YI_LIAN_YUN);
	}
	
	static public enum YlyAccessTokenTypes implements AccessTokenType {
		YI_LIAN_YUN
	}
}

