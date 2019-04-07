package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.onetwo.boot.module.redis.RedisLockRunner;
import org.onetwo.common.apiclient.utils.ApiClientUtils;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.accesstoken.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.util.Assert;

import lombok.Setter;

/**
 * 基于redis
 * @author wayshall
 * <br/>
 */
abstract public class AbstractAccessTokenService implements AccessTokenService, InitializingBean {
	
	protected final Logger logger = ApiClientUtils.getApiclientlogger();

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
	private WechatConfigProvider wechatConfigProvider;
	
	private AccessTokenTypes supportedClientType = AccessTokenTypes.WECHAT;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(redisLockRegistry, "redisLockRegistry not found");
//		Assert.notNull(wechatConfig, "wechat config can not be null");
	}
	
	@Override
	public Optional<AccessTokenInfo> refreshAccessTokenByAppid(String appid) {
		WechatConfig wechatConfig = wechatConfigProvider.getWechatConfig(appid);
		if (appid==null || !appid.equals(wechatConfig.getAppid())) {
			logger.warn("appid error, ignore refresh, appid: {}, configuration appid: {}", appid, wechatConfig.getAppid());
			return Optional.empty();
		}
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
																.appid(appid)
																.secret(wechatConfig.getAppsecret())
															.build();
		AccessTokenInfo tokenInfo = refreshAccessToken(request);
		return Optional.of(tokenInfo);
	}



	abstract protected String getStoreType();
	
	@Override
	public AccessTokenInfo getOrRefreshAccessToken(GetAccessTokenRequest request) {
		Optional<AccessTokenInfo> atOpt = getAccessToken(request.getAppid());
		if(atOpt.isPresent() && !atOpt.get().isExpired()){
			return atOpt.get();
		}
		AccessTokenInfo at = refreshAccessToken(request);
		return at;
	}
	

	public void removeAccessToken(String appid) {
		try {
			//使用锁，防止正在更新的时候，同时又删除
			getRedisLockRunnerByAppId(appid).tryLock(()->{
				if(logger.isInfoEnabled()){
					logger.info("remove accessToken from {} server...", getStoreType());
				}
				removeByAppid(appid);
				return null;
			});
		} catch (Exception e) {
			logger.error("remove appid[" + appid + "] AccessToken error: " + e.getMessage());
		}
	}

	protected AccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
		return accessTokenProvider.getAccessToken(request);
	}

	public AccessTokenInfo refreshAccessToken(GetAccessTokenRequest request){
		AccessTokenInfo at = getRedisLockRunnerByAppId(request.getAppid()).tryLock(()->{
			Optional<AccessTokenInfo> opt = getAccessToken(request.getAppid());
			
			//未过时且最近更新过
			if(opt.isPresent() && !opt.get().isExpired() && opt.get().isUpdatedNewly()){
				if(logger.isInfoEnabled()){
					logger.info("double check access token from {} server...", getStoreType());
				}
				return opt.get();
			}
			
			AccessTokenResponse tokenRes = getAccessToken(request);
			int expired = getExpiresIn(tokenRes);
			AccessTokenInfo newToken = AccessTokenInfo.builder()
														.appid(request.getAppid())
														.accessToken(tokenRes.getAccessToken())
														.expiresIn(expired)
														.updateAt(new Date())
														.build();
			saveNewToken(newToken);
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
	

	/****
	 * 移除
	 * @author weishao zeng
	 * @param appid
	 */
	abstract protected void removeByAppid(String appid);
	
	/***
	 * 刷新后保存
	 * @author weishao zeng
	 * @param newToken
	 */
	abstract protected void saveNewToken(AccessTokenInfo newToken);
	
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


	public void setWechatConfigProvider(WechatConfigProvider wechatConfigProvider) {
		this.wechatConfigProvider = wechatConfigProvider;
	}

	public AccessTokenTypes getSupportedClientType() {
		return supportedClientType;
	}

	public void setSupportedClientType(AccessTokenTypes supportedClientType) {
		this.supportedClientType = supportedClientType;
	}


}
