package org.onetwo.ext.apiclient.wxcommon;

import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;

/**
 * @author weishao zeng
 * <br/>
 */
public interface AccessTokenProvider {
	
	AccessTokenResponse getAccessToken(GetAccessTokenRequest request);
	
}

