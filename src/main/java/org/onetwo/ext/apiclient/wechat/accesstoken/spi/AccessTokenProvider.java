package org.onetwo.ext.apiclient.wechat.accesstoken.spi;

import java.util.List;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;

/**
 * @author weishao zeng
 * <br/>
 */
public interface AccessTokenProvider {
	
//	AccessTokenResponse getAccessToken(GetAccessTokenRequest request);
	AccessTokenResponse getAccessToken(AppidRequest request);
	
	/****
	 * 默认实现是直接重新获取accessToken
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	default AccessTokenResponse refreshAccessToken(AppidRequest request) {
		return getAccessToken(request);
	}
	
	List<AccessTokenType> getAccessTokenTypes();
	
}

