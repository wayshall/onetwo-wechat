package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.concurrent.TimeUnit;

import org.onetwo.boot.module.redis.RedisLockRunner;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
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
		return getAccessToken(request);
	}
	
	@Override
	public AccessTokenInfo getAccessToken(GetAccessTokenRequest request) {
		BoundValueOperations<String, AccessTokenInfo> opt = boundValueOperationsByAppId(request.getAppid());
		AccessTokenInfo at = opt.get();
		if(at!=null && !at.isExpired()){
			return at;
		}
		at = refreshAccessToken(request);
		return at;
	}


	public AccessTokenInfo refreshAccessToken(GetAccessTokenRequest request){
		AccessTokenInfo at = getRedisLockRunnerByAppId(request.getAppid()).tryLock(()->{
			BoundValueOperations<String, AccessTokenInfo> opt = boundValueOperationsByAppId(request.getAppid());
			AccessTokenInfo token = null;
			/*if(doubleCheck){
				token = opt.get();
				if(token!=null && !token.isExpired()){
					return token;
				}
			}*/
			if(logger.isInfoEnabled()){
				logger.info("get access token from wechat server...");
			}
			token = WechatUtils.getAccessToken(wechatServer, request);
			opt.set(token, getExpiresIn(token), TimeUnit.SECONDS);
			if(logger.isInfoEnabled()){
				logger.info("access token : {}", token);
			}
			return token;
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
	private long getExpiresIn(AccessTokenInfo token){
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
