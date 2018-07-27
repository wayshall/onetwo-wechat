package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.onetwo.boot.module.redis.RedisLockRunner;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.util.Assert;

/**
 * 基于redis
 * @author wayshall
 * <br/>
 */
public class RedisStoreAccessTokenService implements AccessTokenService, InitializingBean {
	
	private final Logger logger = JFishLoggerFactory.getLogger(this.getClass());

	@Autowired
	private WechatServer wechatServer;
	@Autowired
	private WechatConfig wechatConfig;
	/*@Autowired
	private JedisConnectionFactory jedisConnectionFactory;*/
	
	@Autowired
	private RedisLockRegistry redisLockRegistry;
//	private RedisLockRunner redisLockRunner;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private int retryLockInSeconds = 1;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(redisTemplate, "redisTemplate not found");
		Assert.notNull(redisLockRegistry, "redisLockRegistry not found");
		Assert.notNull(wechatConfig, "wechat config can not be null");
	}

	public AccessTokenInfo getAccessToken() {
		GetAccessTokenRequest request = WechatUtils.createGetAccessTokenRequest(wechatConfig);
		return getOrRefreshAccessToken(request);
	}
	
	public Optional<AccessTokenInfo> getAccessToken(String appid) {
		Assert.hasText(appid, "appid must have length; it must not be null or empty");
		BoundValueOperations<String, AccessTokenInfo> opt = boundValueOperationsByAppId(appid);
		AccessTokenInfo at = null;
		if(logger.isDebugEnabled()){
			logger.debug("get accessToken from redis server...");
		}
		try {
			at = opt.get();
		} catch (SerializationException e) {
			//序列化错误的时候直接移除
			logger.error("getAccessToken error: " + e.getMessage());
			logger.error("clear for SerializationException accessToen...");
			removeAccessToken(appid);
		}
		return Optional.ofNullable(at);
	}
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
				if(logger.isDebugEnabled()){
					logger.debug("remove accessToken from redis server...");
				}
				String key = WechatUtils.getAccessTokenKey(appid);
				this.redisTemplate.delete(key);
				return null;
			});
		} catch (Exception e) {
			logger.error("remove appid[" + appid + "] AccessToken error: " + e.getMessage());
		}
	}


	public AccessTokenInfo refreshAccessToken(GetAccessTokenRequest request){
		AccessTokenInfo at = getRedisLockRunnerByAppId(request.getAppid()).tryLock(()->{
			BoundValueOperations<String, AccessTokenInfo> opt = boundValueOperationsByAppId(request.getAppid());
//			AccessTokenInfo token = null;
			/*if(doubleCheck){
				token = opt.get();
				if(token!=null && !token.isExpired()){
					return token;
				}
			}*/
			if(logger.isDebugEnabled()){
				logger.debug("get access token from wechat server...");
			}
//			token = WechatUtils.getMaterialList(wechatServer, request);
			AccessTokenResponse tokenRes = wechatServer.getAccessToken(request);
			int expired = getExpiresIn(tokenRes);
			AccessTokenInfo newToken = AccessTokenInfo.builder()
														.appid(request.getAppid())
														.accessToken(tokenRes.getAccessToken())
														.expiresIn(expired)
														.build();
			opt.set(newToken, expired, TimeUnit.SECONDS);
			if(logger.isDebugEnabled()){
				logger.debug("access token : {}", newToken);
			}
			return newToken;
		}, ()->{
			//如果锁定失败，则休息1秒，然后递归……
			if(logger.isWarnEnabled()){
				logger.warn("obtain redisd lock error, sleep {} seconds and retry...", retryLockInSeconds);
			}
			LangUtils.await(retryLockInSeconds);
			return refreshAccessToken(request);
		});
		return at;
	}
	
	/***
	 * 获取设置redis的过期时间，比token有效时间稍短，避免过期
	 * @author wayshall
	 * @param token
	 * @return
	 */
	private int getExpiresIn(AccessTokenResponse token){
//		return token.getExpiresIn();
		return token.getExpiresIn() - AccessTokenInfo.SHORTER_EXPIRE_TIME_IN_SECONDS;
	}
	

	private RedisLockRunner getRedisLockRunnerByAppId(String appid){
		RedisLockRunner redisLockRunner = RedisLockRunner.builder()
														 .lockKey(WechatUtils.LOCK_KEY+appid)
														 .errorHandler(e->{
															 throw new ApiClientException(WechatClientErrors.ACCESS_TOKEN_REFRESH_ERROR, e);
														 })
														 .redisLockRegistry(redisLockRegistry)
														 .build();
		return redisLockRunner;
	}
	
	private BoundValueOperations<String, AccessTokenInfo> boundValueOperationsByAppId(String appid){
		return WechatUtils.boundValueOperationsByAppId(redisTemplate, appid);
	}

	public WechatServer getWechatServer() {
		return wechatServer;
	}

	public WechatConfig getWechatConfig() {
		return wechatConfig;
	}

	public void setRetryLockInSeconds(int retryLockInSeconds) {
		this.retryLockInSeconds = retryLockInSeconds;
	}

}
