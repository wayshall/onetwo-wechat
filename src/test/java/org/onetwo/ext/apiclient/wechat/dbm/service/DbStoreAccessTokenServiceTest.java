package org.onetwo.ext.apiclient.wechat.dbm.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.WechatDbmTestsAdapter;
import org.onetwo.ext.apiclient.wechat.basic.response.GetCallbackIpResponse;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatErrors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class DbStoreAccessTokenServiceTest extends WechatDbmTestsAdapter{
	
	@Autowired
	private DbStoreAccessTokenService dbStoreAccessTokenService;
	@Autowired
	private AccessTokenRepository accessTokenRepository;
	

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
		
		// 测试自动刷新，必须等待五秒以上
		accessTokenInfo.setAccessToken("error");
		accessTokenRepository.save(accessTokenInfo);
		LangUtils.await(AccessTokenInfo.UPDATE_NEWLY_DURATION_SECONDS + 1);
		
		response = wechatServer.getCallbackIp(accessTokenInfo);
		
		// 不等待会出错
		accessTokenInfo.setAccessToken("error");
		accessTokenRepository.save(accessTokenInfo);
		assertThatThrownBy(() -> {
			wechatServer.getCallbackIp(accessTokenInfo);
		})
		.isExactlyInstanceOf(ApiClientException.class)
		.hasFieldOrPropertyWithValue("errorType", WechatErrors.ACCESS_TOKEN_INVALID_CREDENTIAL);
		
	}
}

