package org.onetwo.ext.apiclient.wechat.basic.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
import org.onetwo.ext.apiclient.wechat.oauth2.api.WechatOauth2Client;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class WechatOauth2ClientTest extends WechatBaseTestsAdapter {
	
	@Autowired
	WechatOauth2Client wechatOauth2Client;
	
	@Test
	public void testCreateAuthorize(){
		AuthorizeData authorizeData = wechatOauth2Client.createAuthorize("http://test.com", "test");
		System.out.println("authorizeData: " + authorizeData);
		assertThat(authorizeData.getAppid()).isNotEmpty();
		System.out.println("toAuthorizeUrl: " + authorizeData.toAuthorizeUrl());
	}
	
	/*@Test
	public void testetAccessToken(){
		Oauth2AccessTokenRequest request = Oauth2AccessTokenRequest.builder()
																	.code("test")
																	.build();
		Oauth2AccessTokenResponse tokenResponse = wechatOauth2Client.getAccessToken(request);
		System.out.println("authorizeData: " + tokenResponse);
	}*/

}
