package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.onetwo.boot.module.redis.RedisLockRunner;
import org.onetwo.common.apiclient.utils.ApiClientUtils;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AppCacheKeyGenerator;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.event.WechatEventBus;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;

/**
 * TODO: 注意企业微信有可能存在这样的情况：同一个appid（cropid），但是使用不同应用的秘钥获取不同的accesstoken，
	 * 此时如果根据appid来识别不同的token是无法做到的，需要另外处理。
	 * 使用appid+secrect作为store key？或者根据两者生成唯一hash，然后保存到AccessTokenInfo，刷新的时候调用？在AppidRequest增加标识？
	 * 
 * @author wayshall
 * <br/>
 */
abstract public class AbstractAccessTokenService implements AccessTokenService, InitializingBean {
	
	protected final Logger logger = ApiClientUtils.getApiclientlogger();

	@Getter
	private AccessTokenProvider accessTokenProvider;
//	@Autowired
//	private WechatConfig wechatConfig;
	/*@Autowired
	private JedisConnectionFactory jedisConnectionFactory;*/
	
	@Autowired
	private RedisLockRegistry redisLockRegistry;
//	private RedisLockRunner redisLockRunner;
	@Setter
	private long lockWaitInSeconds = 1;
	
//	@Autowired
//	protected WechatConfig wechatConfig;
//	private WechatConfigProvider wechatConfigProvider;
	
	@Autowired(required=false)
	private WechatEventBus wechatEventBus;
	
	@Autowired
	private AppCacheKeyGenerator appCacheKeyGenerator;
	
//	private AccessTokenTypes supportedClientType = AccessTokenTypes.WECHAT;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(redisLockRegistry, "redisLockRegistry not found");
//		Assert.notNull(wechatConfig, "wechat config can not be null");
	}
	
	@Override
	public Optional<AccessTokenInfo> refreshAccessTokenByAppid(AppidRequest refreshTokenRequest) {
		AccessTokenResponse res = accessTokenProvider.getAccessToken(refreshTokenRequest);
		AccessTokenInfo token = AccessTokenInfo.builder()
											.accessToken(res.getAccessToken())
											.expiresIn(res.getExpiresIn())
											.updateAt(new Date())
										.build();
		return Optional.of(token);
		
//		String appid = refreshTokenRequest.getAppid();
//		WechatConfig wechatConfig = wechatConfigProvider.getWechatConfig(appid);
//		WechatUtils.assertWechatConfigNotNull(wechatConfig, appid);
//		if (appid==null || !appid.equals(wechatConfig.getAppid())) {
//			logger.warn("appid error, ignore refresh, appid: {}, configuration appid: {}", appid, wechatConfig.getAppid());
//			return Optional.empty();
//		}
//		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
//																.appid(appid)
//																.secret(wechatConfig.getAppsecret())
//																.accessTokenType(refreshTokenRequest.getAccessTokenType())
//															.build();
//		AccessTokenInfo tokenInfo = refreshAccessToken(request);
//		return Optional.of(tokenInfo);
	}



	abstract protected String getStoreType();
	
	@Override
	public AccessTokenInfo getOrRefreshAccessToken(GetAccessTokenRequest request) {
		this.removeByAppid(request);
		AppidRequest appidRequest = new AppidRequest(request.getAppid(), request.getAgentId(), request.getAccessTokenType());
		Optional<AccessTokenInfo> atOpt = getAccessToken(appidRequest);
//		if(atOpt.isPresent() && !atOpt.get().isExpired()){
//			return atOpt.get();
//		}
		AccessTokenInfo at = null;
		if (!atOpt.isPresent()) {
			// 若缓存里返回到accessToken为null，则调用 accessTokenProvider#getAccessToken 方法
			AccessTokenResponse tokenRes = getAccessToken(request);
			if (tokenRes!=null) {
				at = toAccessTokenInfo(request, tokenRes);
			}
		} else {
			at = atOpt.get();
		}
		if (at==null || at.isExpired()) {
			at = refreshAccessToken(request);
		}
		return at;
	}
	

	public void removeAccessToken(AppidRequest appidRequest) {
		String appid = getAppidKey(appidRequest);
		try {
			//使用锁，防止正在更新的时候，同时又删除
			getRedisLockRunnerByAppId(appid).tryLock(()->{
				if(logger.isInfoEnabled()){
					logger.info("remove accessToken from {} server...", getStoreType());
				}
				removeByAppid(appidRequest);
				return null;
			});
		} catch (Exception e) {
			logger.error("remove appid[" + appid + "] AccessToken error: " + e.getMessage());
		}
	}

	protected AccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
//		AppidRequest appidRequest = new AppidRequest();
//		appidRequest.setAppid(request.getAppid());
//		appidRequest.setAccessTokenType(request.getAccessTokenType());
//		appidRequest.setAgentId(request.getAgentId());
		return accessTokenProvider.getAccessToken(request);
	}
	
	/***
	 * 根据appid获取缓存的token
	 */
	abstract public Optional<AccessTokenInfo> getAccessToken(AppidRequest appidRequest);
	
	protected boolean isUpdatedNewly(Optional<AccessTokenInfo> opt) {
		if(opt.isPresent() && !opt.get().isExpired() && opt.get().isUpdatedNewly()){
			return true;
		}
		return false;
	}

	public AccessTokenInfo refreshAccessToken(GetAccessTokenRequest request){
		AccessTokenInfo at = getRedisLockRunnerByAppId(request.getAppid()).tryLock(()->{
			AppidRequest appidRequest = new AppidRequest(request.getAppid(), request.getAgentId(), request.getAccessTokenType());
			Optional<AccessTokenInfo> opt = getAccessToken(appidRequest);
			
			//未过时且最近更新过
			if(isUpdatedNewly(opt)){
				if(logger.isInfoEnabled()){
					logger.info("double check access token from {} server...", getStoreType());
				}
				return opt.get();
			}
			
			AccessTokenResponse tokenRes = accessTokenProvider.refreshAccessToken(request);
			AccessTokenInfo newToken = toAccessTokenInfo(request, tokenRes);
			saveNewToken(newToken, appidRequest);
			
			if (this.wechatEventBus!=null) {
				wechatEventBus.postRefreshedEvent(appidRequest, newToken);
			}
			
			if(logger.isInfoEnabled()){
				logger.info("saved new access token : {}", newToken);
			}
			return newToken;
		}, ()->{
			//如果锁定失败，则休息1秒，然后递归……
			int retryLockInSeconds = 1;
			if(logger.isWarnEnabled()){
				logger.warn("obtain redisd lock error, sleep {} seconds and retry...", retryLockInSeconds);
			}
			LangUtils.await(retryLockInSeconds);
			return refreshAccessToken(request);
		});
		return at;
	}
	
	protected AccessTokenInfo toAccessTokenInfo(GetAccessTokenRequest request, AccessTokenResponse tokenRes) {
		int expired = getExpiresIn(tokenRes);
		AccessTokenInfo newToken = AccessTokenInfo.builder()
													.appid(request.getAppid())
													.accessToken(tokenRes.getAccessToken())
													.expiresIn(expired)
													.agentId(request.getAgentId())
													.updateAt(new Date())
													.expireAt(tokenRes.getExpireAt())
													.build();
		return newToken;
	}
	

	/****
	 * 从缓存中移除accessToken
	 * @author weishao zeng
	 * @param appid
	 */
	abstract protected void removeByAppid(AppidRequest appid);
	
	/***
	 * 刷新后保存
	 * TODO: 注意企业微信有可能存在这样的情况：同一个appid（cropid），但是使用不同应用的秘钥获取不同的accesstoken，
	 * 此时如果根据appid来识别不同的token是无法做到的，需要另外处理。
	 * 使用appid+secrect作为store key？或者根据两者生成唯一hash，然后保存到AccessTokenInfo，刷新的时候调用？在AppidRequest增加标识？
	 * @author weishao zeng
	 * @param newToken
	 */
	abstract protected void saveNewToken(AccessTokenInfo newToken, AppidRequest appidRequest);
	
	/***
	 * 获取设置redis的过期时间，比token有效时间稍短，避免过期
	 * @author wayshall
	 * @param token
	 * @return
	 */
	private int getExpiresIn(AccessTokenResponse token){
		return token.getExpiresIn();
//		return token.getExpiresIn() - AccessTokenInfo.SHORTER_EXPIRE_TIME_IN_SECONDS;
	}
	

	private RedisLockRunner getRedisLockRunnerByAppId(String appid){
		RedisLockRunner redisLockRunner = RedisLockRunner.builder()
														 .lockKey(WechatUtils.LOCK_KEY+appid)
														 .time(lockWaitInSeconds)
														 .unit(TimeUnit.SECONDS)
														 .errorHandler(e->{
															 throw new ApiClientException(WechatClientErrors.ACCESS_TOKEN_REFRESH_ERROR, e);
														 })
														 .redisLockRegistry(redisLockRegistry)
														 .build();
		return redisLockRunner;
	}
	
	public void setAccessTokenProvider(AccessTokenProvider accessTokenProvider) {
		this.accessTokenProvider = accessTokenProvider;
	}


//	public void setWechatConfigProvider(WechatConfigProvider wechatConfigProvider) {
//		this.wechatConfigProvider = wechatConfigProvider;
//	}

	protected String getAppidKey(AppidRequest appidRequest) {
		return appCacheKeyGenerator.generated(appidRequest);
	}

	/*public AccessTokenTypes getSupportedClientType() {
		return supportedClientType;
	}

	public void setSupportedClientType(AccessTokenTypes supportedClientType) {
		this.supportedClientType = supportedClientType;
	}*/


}
