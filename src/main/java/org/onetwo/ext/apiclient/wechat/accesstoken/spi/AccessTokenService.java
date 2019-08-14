package org.onetwo.ext.apiclient.wechat.accesstoken.spi;

import java.util.Optional;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;

/**
 * @author wayshall
 * <br/>
 */
public interface AccessTokenService {

	/***
	 * 不传参数，默认读取配置的
	 * @author wayshall
	 * @return
	 */
	/*@Deprecated
	AccessTokenInfo getAccessToken();*/
	AccessTokenInfo getOrRefreshAccessToken(GetAccessTokenRequest request);
	Optional<AccessTokenInfo> refreshAccessTokenByAppid(AppidRequest appidRequest);
	AccessTokenInfo refreshAccessToken(GetAccessTokenRequest request);
	void removeAccessToken(AppidRequest appidRequest);
	/***
	 * 根据appid获取缓存的token
	 * @author wayshall
	 * @param appid
	 * @return
	 */
	Optional<AccessTokenInfo> getAccessToken(AppidRequest appidRequest);
	
	/***
	 * tokenService支持哪种类型的client
	 * @author weishao zeng
	 * @return
	 */
//	AccessTokenTypes getSupportedClientType();
	
}