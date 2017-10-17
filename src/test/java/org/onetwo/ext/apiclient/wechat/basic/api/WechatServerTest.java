package org.onetwo.ext.apiclient.wechat.basic.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.basic.request.AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.GetCallbackIpResponse;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.support.impl.RedisRefreshAccessTokenTask;
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
	@Autowired(required=false)
	RedisRefreshAccessTokenTask redisRefreshAccessTokenTask;
	
	@Test
	public void testGetCallbackIp(){
		String accessToken = accessTokenService.getAccessToken().getAccessToken();
		assertThat(accessToken).isNotNull();
		assertThat(accessToken).isNotEmpty();
		
		GetCallbackIpResponse response = wechatServer.getCallbackIp(AccessTokenRequest.accessTokenRequest()
																				.accessToken(accessToken)
																				.build());
		assertThat(response).isNotNull();
//		assertThat(response.isSuccess()).isTrue();
		assertThat(response.getIpList()).isNotEmpty();
		
		//test for refresh token
		if(redisRefreshAccessTokenTask!=null){
			redisRefreshAccessTokenTask.scheduleRefreshTask();
		}
		response = wechatServer.getCallbackIp(AccessTokenRequest.accessTokenRequest()
								.accessToken(accessToken)
								.build());
		assertThat(response).isNotNull();
		//assertThat(response.isSuccess()).isTrue();
		assertThat(response.getIpList()).isNotEmpty();
		
		//不手动设置accessToken
		response = wechatServer.getCallbackIp(AccessTokenRequest.accessTokenRequest().build());
		assertThat(response).isNotNull();
		assertThat(response.getIpList()).isNotEmpty();
	}

}
