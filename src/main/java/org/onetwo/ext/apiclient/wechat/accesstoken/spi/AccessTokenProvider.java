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
	
	List<AccessTokenType> getAccessTokenTypes();
	
}

