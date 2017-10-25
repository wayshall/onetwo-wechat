package org.onetwo.ext.apiclient.wechat.utils;

import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.GrantTypeKeys;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author wayshall
 * <br/>
 */
public class WechatUtils {

	public static final String ACCESS_TOKEN_PREFIX = "wechat_accesstoken_";
	public static final String LOCK_KEY = "lock_wechat_acesstoken_";
	
	public static GetAccessTokenRequest createGetAccessTokenRequest(WechatAppInfo wechatAppInfo){
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
															.grantType(GrantTypeKeys.CLIENT_CREDENTIAL)
															.appid(wechatAppInfo.getAppid())
															.secret(wechatAppInfo.getAppsecret())
															.build();
		return request;
	}
	
	public static GetAccessTokenRequest createGetAccessTokenRequest(WechatConfig wechatConfig){
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
				.grantType(wechatConfig.getGrantType())
				.appid(wechatConfig.getAppid())
				.secret(wechatConfig.getAppsecret())
				.build();
		return request;
	}
	
	public static AccessTokenInfo getAccessToken(WechatServer wechatServer, GetAccessTokenRequest request){
		
		AccessTokenResponse response = wechatServer.getAccessToken(request);
		AccessTokenInfo accessToken = AccessTokenInfo.builder()
													.accessToken(response.getAccessToken())
													.expiresIn(response.getExpiresIn())
													.build();
		return accessToken;
	}
	
	@SuppressWarnings("unchecked")
	public static BoundValueOperations<String, AccessTokenInfo> boundValueOperationsByAppId(RedisTemplate<String, ?> redisTemplate, String appid){
		String key = getAccessTokenKey(appid);
		BoundValueOperations<String, AccessTokenInfo> opt = (BoundValueOperations<String, AccessTokenInfo>)redisTemplate.boundValueOps(key);
		return opt;
	}

	public static String getAccessTokenKey(String appid){
		return WechatUtils.ACCESS_TOKEN_PREFIX + appid;
	}
	
	private WechatUtils(){
	}

}
