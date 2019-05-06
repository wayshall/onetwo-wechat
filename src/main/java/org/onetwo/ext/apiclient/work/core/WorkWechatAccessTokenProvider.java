package org.onetwo.ext.apiclient.work.core;

import java.util.Arrays;
import java.util.List;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.work.basic.api.WorkTokenClient;
import org.onetwo.ext.apiclient.work.basic.api.WorkTokenClient.GetTokenRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class WorkWechatAccessTokenProvider implements AccessTokenProvider {
	@Autowired
	private WorkTokenClient workTokenClient;

	@Override
	public AccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
		GetTokenRequest getTokenRequest = GetTokenRequest.builder()
														.corpid(request.getAppid())
														.corpsecret(request.getSecret())
														.build();
		return workTokenClient.getAccessToken(getTokenRequest);
	}
	
	@Override
	public List<AccessTokenTypes> getAccessTokenTypes() {
		return Arrays.asList(AccessTokenTypes.WORK_WECHAT, AccessTokenTypes.CONTACTS);
	}

}

