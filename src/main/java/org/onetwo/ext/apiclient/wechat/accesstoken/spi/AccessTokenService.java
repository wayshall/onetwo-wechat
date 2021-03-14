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
	 * 获取或者自动刷新accessToken
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	AccessTokenInfo getOrRefreshAccessToken(GetAccessTokenRequest request);
	
	/****
	 * 调用 getOrRefreshAccessToken 获取accessToken过期时，调用此方法刷新token
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	AccessTokenInfo refreshAccessToken(GetAccessTokenRequest request);
	
	/****
	 * api返回token过期错误时，会调用此方法刷新（重新获取）accessToken
	 * @see WechatClientMethodInterceptor#processAutoRemove
	 * @author weishao zeng
	 * @param appidRequest
	 * @return
	 */
	Optional<AccessTokenInfo> refreshAccessTokenByAppid(AppidRequest appidRequest);
	
	/****
	 * 从缓存中移除accessToken
	 * @author weishao zeng
	 * @param appidRequest
	 */
	void removeAccessToken(AppidRequest appidRequest);
	
	/***
	 * 根据appid获取缓存的token
	 * @author wayshall
	 * @param appid
	 * @return
	 
	Optional<AccessTokenInfo> getAccessToken(AppidRequest appidRequest);*/
	
}