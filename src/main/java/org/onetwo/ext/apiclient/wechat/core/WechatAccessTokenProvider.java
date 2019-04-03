package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wxcommon.AccessTokenProvider;
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

}

