package org.onetwo.ext.apiclient.wechat.basic.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTests;
import org.onetwo.ext.apiclient.wechat.basic.request.Oauth2AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
import org.onetwo.ext.apiclient.wechat.basic.response.Oauth2AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class WechatOauth2ClientTest extends WechatBaseTests {
	
	@Autowired
	WechatOauth2Client wechatOauth2Client;
	
	@Test
	public void testCreateAuthorize(){
		AuthorizeData authorizeData = wechatOauth2Client.createAuthorize();
		System.out.println("authorizeData: " + authorizeData);
		assertThat(authorizeData.getAppid()).isNotEmpty();
		System.out.println("toAuthorizeUrl: " + authorizeData.toAuthorizeUrl());
	}
	
	@Test
	public void testetAccessToken(){
		Oauth2AccessTokenRequest request = Oauth2AccessTokenRequest.builder()
																	.code("test")
																	.build();
		Oauth2AccessTokenResponse tokenResponse = wechatOauth2Client.getAccessToken(request);
		System.out.println("authorizeData: " + tokenResponse);
	}

}
