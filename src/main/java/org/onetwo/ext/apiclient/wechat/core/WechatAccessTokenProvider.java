package org.onetwo.ext.apiclient.wechat.core;

import java.util.Arrays;
import java.util.List;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class WechatAccessTokenProvider implements AccessTokenProvider {
	@Autowired
	private WechatServer wechatServer;

	@Override
	public AccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
		return wechatServer.getAccessToken(request);
	}

	@Override
	public List<AccessTokenTypes> getAccessTokenTypes() {
		return Arrays.asList(AccessTokenTypes.WECHAT);
	}

}

