package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;

/**
 * 基于内存
 * @author wayshall
 * <br/>
 */
public class MemoryAccessTokenService implements AccessTokenService {
	private final Logger logger = JFishLoggerFactory.getLogger(this.getClass());

	@Autowired
	private WechatServer wechatServer;
	/*@Autowired
	private WechatConfig wechatConfig;*/
	
//	private volatile AccessTokenInfo accessToken;
//	private ReentrantLock obtainAcessTokenLock = new ReentrantLock();
	private Cache<String, AccessTokenInfo> accessTokenCaches = CacheBuilder.newBuilder()
																		.expireAfterWrite(120, TimeUnit.MINUTES)
																		.build();
	private Map<String, ReentrantLock> lockMap = Maps.newConcurrentMap();
	@Autowired
	protected WechatConfig wechatConfig;
	
	@Override
	public Optional<AccessTokenInfo> getAccessToken(String appid) {
		Assert.hasText(appid, "appid must have length; it must not be null or empty");
		AccessTokenInfo at = accessTokenCaches.getIfPresent(appid);
		return Optional.ofNullable(at);
	}


	@Override
	public Optional<AccessTokenInfo> refreshAccessTokenByAppid(String appid) {
		if (appid==null || !appid.equals(wechatConfig.getAppid())) {
			return Optional.empty();
		}
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
																.appid(appid)
																.secret(wechatConfig.getAppsecret())
															.build();
		AccessTokenInfo tokenInfo = refreshAccessToken(request);
		return Optional.of(tokenInfo);
	}

	@Override
	public AccessTokenInfo getOrRefreshAccessToken(GetAccessTokenRequest request) {
		AccessTokenInfo at = this.getAccessTokenFromCache(request);
		if(at!=null && !at.isExpired()){
			return at;
		}
		at = refreshAccessToken(request);
		return at;
	}

	/*protected AccessTokenInfo obtainAccessToken(GetAccessTokenRequest request){
		ReentrantLock appidLock = getLockByAppId(request.getAppid());
		try {
			appidLock.lock();
			AccessTokenInfo accessToken = this.getAccessTokenInfoByAppId(request.getAppid());
			if(accessToken!=null && !accessToken.isExpired()){
				return accessToken;
			}
			accessToken = WechatUtils.getMaterialList(wechatServer, request);
			this.accessTokenCaches.put(request.getAppid(), accessToken);
			return accessToken;
		} finally{
			appidLock.unlock();
		}
	}*/
	
	private ReentrantLock getLockByAppId(String appid){
		ReentrantLock obtainAcessTokenLock = lockMap.get(appid);
		if(obtainAcessTokenLock==null){
			final ReentrantLock newLock = new ReentrantLock();
			obtainAcessTokenLock = this.lockMap.putIfAbsent(appid, newLock);
			if(obtainAcessTokenLock==null){//put成功，否则，put失败
				obtainAcessTokenLock = newLock;
			}
		}
		return obtainAcessTokenLock;
	}
	
	@Override
	public AccessTokenInfo refreshAccessToken(GetAccessTokenRequest request) {
		AccessTokenInfo token = null;
		ReentrantLock appidLock = getLockByAppId(request.getAppid());
		try {
			appidLock.lock();
			String key = WechatUtils.getAccessTokenKey(request.getAppid());
			/*if(checkAgain){
				token = this.accessTokenCaches.getIfPresent(key);
				if(token!=null && !token.isExpired()){
					return token;
				}
			}*/
			if(logger.isInfoEnabled()){
				logger.info("==========>>> get access token from wechat server...");
			}
			this.accessTokenCaches.invalidate(key);
			token = getAccessTokenFromCache(request);
		} finally{
			appidLock.unlock();
		}
		
		return token;
	}

	private AccessTokenInfo getAccessTokenFromCache(GetAccessTokenRequest request){
		String key = WechatUtils.getAccessTokenKey(request.getAppid());
		try {
			return this.accessTokenCaches.get(key, ()->{
				AccessTokenInfo accessToken = WechatUtils.getAccessToken(wechatServer, request);
				return accessToken;
			});
		} catch (ExecutionException e) {
			throw new WechatException(WechatClientErrors.ACCESS_TOKEN_OBTAIN_ERROR);
		}
	}
	
	

	@Override
	public void removeAccessToken(String appid) {
		try {
			String key = WechatUtils.getAccessTokenKey(appid);
			this.accessTokenCaches.invalidate(key);
		} catch (Exception e) {
			logger.error("remove appid[" + appid + "] AccessToken error: " + e.getMessage());
		}
	}

	public WechatServer getWechatServer() {
		return wechatServer;
	}

	/*public WechatConfig getWechatConfig() {
		return wechatConfig;
	}*/

}
