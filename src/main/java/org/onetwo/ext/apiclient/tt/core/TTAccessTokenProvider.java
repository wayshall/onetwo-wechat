package org.onetwo.ext.apiclient.tt.core;

import java.util.Arrays;
import java.util.List;

import org.onetwo.ext.apiclient.tt.api.TTAccessTokenApi;
import org.onetwo.ext.apiclient.tt.request.TTGetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenType;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class TTAccessTokenProvider implements AccessTokenProvider {
	@Autowired
	TTAccessTokenApi ttAccessTokenApi;
	@Autowired
	private TTAppConfig appConfig;
	
	@Override
	public AccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
		WechatConfig appconfig = appConfig.getAppConfig(request.getAppid());
		
		TTGetAccessTokenRequest ttRequest = new TTGetAccessTokenRequest();
		ttRequest.setAppid(request.getAppid());
		ttRequest.setGrantType(request.getGrantType());
		ttRequest.setSecret(appconfig.getAppsecret());
		
		return ttAccessTokenApi.getAccessToken(ttRequest);
	}

	@Override
	public List<AccessTokenType> getAccessTokenTypes() {
		return Arrays.asList(TTAccessTokenTypes.TTAPP);
	}
	
	static public enum TTAccessTokenTypes implements AccessTokenType {
		TTAPP
	}
}

