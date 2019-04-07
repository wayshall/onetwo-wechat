package org.onetwo.ext.apiclient.wechat.accesstoken;

import java.util.List;

import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;

/**
 * @author weishao zeng
 * <br/>
 */
public interface AccessTokenProvider {
	
	AccessTokenResponse getAccessToken(GetAccessTokenRequest request);
	
	List<AccessTokenTypes> getAccessTokenTypes();
	
}

