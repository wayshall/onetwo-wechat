package org.onetwo.ext.apiclient.wechat.basic.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTests;
import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
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

}
