package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;

/**
 * @author wayshall
 * <br/>
 */
public interface AccessTokenService {

	AccessTokenInfo getAccessToken();
	AccessTokenInfo getAccessToken(GetAccessTokenRequest request);
	AccessTokenInfo refreshAccessToken(GetAccessTokenRequest request);
	void removeAccessToken(String appid);
}