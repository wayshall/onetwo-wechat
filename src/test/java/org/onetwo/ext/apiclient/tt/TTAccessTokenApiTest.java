package org.onetwo.ext.apiclient.tt;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.tt.api.TTAccessTokenApi;
import org.onetwo.ext.apiclient.tt.request.TTGetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.core.WechatConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;

public class TTAccessTokenApiTest extends TTBaseBootTests {
	
	@Autowired
	TTAccessTokenApi ttAccessTokenApi;
	@Autowired
	WechatConfigProvider wechatConfigProvider;
	
	@Test
	public void testGetAccessToken() {
		WechatConfig appconfig = wechatConfigProvider.getWechatConfig("tt054873f59a62f7cf");
		TTGetAccessTokenRequest request = TTGetAccessTokenRequest.builder()
													.appid(appconfig.getAppid())
													.secret(appconfig.getAppsecret())
													.build();
		AccessTokenResponse token = ttAccessTokenApi.getAccessToken(request);
		assertThat(token).isNotNull();
		System.out.println("token: " + token);
		assertThat(token.getAccessToken()).isNotEmpty();
	}

}

