package org.onetwo.ext.apiclient.wechat.basic.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTests;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.request.BaseRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.basic.response.GetCallbackIpResponse;
import org.onetwo.ext.apiclient.wechat.support.BaseSupportService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class WechatServerTest extends WechatBaseTests {
	
	@Autowired
	WechatServer wechatServer;
	@Autowired
	BaseSupportService baseSupportService;
	
	@Test
	public void testGetCallbackIp(){
		AccessTokenResponse accessToken = baseSupportService.getAccessToken();
		assertThat(accessToken).isNotNull();
		assertThat(accessToken.getAccessToken()).isNotEmpty();
		
		GetCallbackIpResponse response = wechatServer.getCallbackIp(BaseRequest.baseRequest()
																				.accessToken(accessToken.getAccessToken())
																				.build());
		assertThat(response).isNotNull();
//		assertThat(response.isSuccess()).isTrue();
		assertThat(response.getIpList()).isNotEmpty();
	}

}
