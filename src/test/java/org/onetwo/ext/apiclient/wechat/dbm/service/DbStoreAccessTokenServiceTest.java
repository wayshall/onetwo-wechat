package org.onetwo.ext.apiclient.wechat.dbm.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatDbmTestsAdapter;
import org.onetwo.ext.apiclient.wechat.basic.response.GetCallbackIpResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class DbStoreAccessTokenServiceTest extends WechatDbmTestsAdapter{
	
	@Autowired
	private DbStoreAccessTokenService dbStoreAccessTokenService;
	

	@Test
	public void testGetCallbackIp(){
		assertThat(dbStoreAccessTokenService).isNotNull();
		
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
		
	}
}

