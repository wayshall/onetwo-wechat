package org.onetwo.ext.apiclient.work.core;

import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.work.basic.api.WorkTokenClient;
import org.onetwo.ext.apiclient.work.basic.api.WorkTokenClient.GetTokenRequest;
import org.onetwo.ext.apiclient.wxcommon.AccessTokenProvider;
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

}

