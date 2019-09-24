package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.slf4j.Logger;
import org.springframework.util.Assert;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 基于内存
 * @author wayshall
 * <br/>
 */
public class MemoryAccessTokenService extends AbstractAccessTokenService implements AccessTokenService {
	private final Logger logger = JFishLoggerFactory.getLogger(this.getClass());

	/*@Autowired
	private WechatConfig wechatConfig;*/
	
//	private volatile AccessTokenInfo accessToken;
//	private ReentrantLock obtainAcessTokenLock = new ReentrantLock();
	private Cache<String, AccessTokenInfo> accessTokenCaches = CacheBuilder.newBuilder()
																		.expireAfterWrite(120, TimeUnit.MINUTES)
																		.build();
//	private Map<String, ReentrantLock> lockMap = Maps.newConcurrentMap();
//	private WechatConfigProvider wechatConfigProvider;
	
	@Override
	protected String getStoreType() {
		return "memory";
	}


	@Override
	protected void removeByAppid(AppidRequest appid) {
		try {
			String key = getAppidKey(appid);
			this.accessTokenCaches.invalidate(key);
		} catch (Exception e) {
			logger.error("remove appid[" + appid + "] AccessToken error: " + e.getMessage());
		}
	}
	

	@Override
	protected void saveNewToken(AccessTokenInfo newToken, AppidRequest appidRequest) {
		throw new UnsupportedOperationException();
	}



	@Override
	public Optional<AccessTokenInfo> getAccessToken(AppidRequest appidRequest) {
		Assert.hasText(appidRequest.getAppid(), "appid must have length; it must not be null or empty");
		String appidKey = getAppidKey(appidRequest);
		AccessTokenInfo at = accessTokenCaches.getIfPresent(appidKey);
		return Optional.ofNullable(at);
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
	
	@Override
	public synchronized AccessTokenInfo refreshAccessToken(GetAccessTokenRequest request) {
		AppidRequest appidRequest = new AppidRequest(request.getAppid(), request.getAgentId(), request.getAccessTokenType());
		Optional<AccessTokenInfo> opt = getAccessToken(appidRequest);
		if(isUpdatedNewly(opt)){
			if(logger.isInfoEnabled()){
				logger.info("double check access token from {} server...", getStoreType());
			}
			return opt.get();
		}
		
		AccessTokenInfo token = null;
		String key = getAppidKey(appidRequest);
		if(logger.isInfoEnabled()){
			logger.info("==========>>> get access token from wechat server...");
		}
		this.accessTokenCaches.invalidate(key);
		token = getAccessTokenFromCache(request);
		
		return token;
	}

	private AccessTokenInfo getAccessTokenFromCache(GetAccessTokenRequest request){
		AppidRequest appidRequest = new AppidRequest(request.getAppid(), request.getAgentId(), request.getAccessTokenType());
		String key = getAppidKey(appidRequest);
		try {
			return this.accessTokenCaches.get(key, ()->{
//				AccessTokenInfo accessToken = WechatUtils.getAccessToken(wechatServer, request);
				AccessTokenResponse response = this.getAccessTokenProvider().getAccessToken(request);
				AccessTokenInfo at = WechatUtils.toAccessTokenInfo(request.getAppid(), response);
				at.setUpdateAt(new Date());
				return at;
			});
		} catch (ExecutionException e) {
			throw new WechatException(WechatClientErrors.ACCESS_TOKEN_OBTAIN_ERROR);
		}
	}
	


	/*public WechatConfig getWechatConfig() {
		return wechatConfig;
	}*/

}
