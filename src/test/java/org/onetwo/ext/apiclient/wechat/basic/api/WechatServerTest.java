package org.onetwo.ext.apiclient.wechat.basic.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.basic.response.GetCallbackIpResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class WechatServerTest extends WechatBaseTestsAdapter {
	
	@Autowired
	WechatServer wechatServer;
	@Autowired
	AccessTokenService accessTokenService;
//	@Autowired(required=false)
//	RedisRefreshAccessTokenTask redisRefreshAccessTokenTask;
	
	@Test
	public void testGetCallbackIp(){
		String accessToken = getAccessToken().getAccessToken();
		assertThat(accessToken).isNotNull();
		assertThat(accessToken).isNotEmpty();
		
		GetCallbackIpResponse response = wechatServer.getCallbackIp(accessTokenInfo);
		assertThat(response).isNotNull();
//		assertThat(response.isSuccess()).isTrue();
		assertThat(response.getIpList()).isNotEmpty();
		
		//test for refresh token
		response = wechatServer.getCallbackIp(accessTokenInfo);
		assertThat(response).isNotNull();
		//assertThat(response.isSuccess()).isTrue();
		assertThat(response.getIpList()).isNotEmpty();
		
		// 测试自动刷新
		accessTokenInfo.setAccessToken("error");
		response = wechatServer.getCallbackIp(accessTokenInfo);
	}

}
