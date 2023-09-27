package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.onetwo.boot.module.redis.RedisUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

/**
 * 基于redis
 * @author wayshall
 * <br/>
 */
public class RedisStoreAccessTokenService extends AbstractAccessTokenService {
	
//	@Autowired
	private RedisTemplate<String, AccessTokenInfo> redisTemplate;
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
//	@Autowired
//	private SimpleRedisOperationService redisOperationService;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		this.redisTemplate = RedisUtils.createJsonValueRedisTemplate(redisConnectionFactory);
//		redisTemplate.afterPropertiesSet();
//		this.redisTemplate = RedisUtils.createJsonRedisTemplate(redisConnectionFactory);
//		Assert.notNull(redisTemplate, "redisTemplate not found");
//		Assert.notNull(wechatConfig, "wechat config can not be null");
	}
	
	@Override
	protected String getStoreType() {
		return "redis";
	}


	@Override
	protected void removeByAppid(AppidRequest appidRequest) {
		String key = getAppidKey(appidRequest);
		this.redisTemplate.delete(key);
	}


	@Override
	protected void saveNewToken(AccessTokenInfo newToken, AppidRequest appidRequest) {
		BoundValueOperations<String, AccessTokenInfo> opt = boundValueOperationsByAppId(appidRequest);
		long expired = newToken.getExpiresIn();
		if (expired > 0) {
			opt.set(newToken, expired, TimeUnit.SECONDS);
		} else {
			// 不过期
			opt.set(newToken);
		}
	}


	public Optional<AccessTokenInfo> getAccessToken(AppidRequest appidRequest) {
		Assert.hasText(appidRequest.getAppid(), "appid must have length; it must not be null or empty");
		BoundValueOperations<String, AccessTokenInfo> opt = boundValueOperationsByAppId(appidRequest);
		AccessTokenInfo at = null;
		if(logger.isDebugEnabled()){
			logger.debug("get accessToken from redis server...");
		}
		try {
			at = (AccessTokenInfo)opt.get();
		} catch (SerializationException e) {
			//序列化错误的时候直接移除
			logger.error("getAccessToken error: " + e.getMessage());
			logger.error("clear for SerializationException accessToen...");
			removeAccessToken(appidRequest);
		}
		return Optional.ofNullable(at);
	}
	
	private BoundValueOperations<String, AccessTokenInfo> boundValueOperationsByAppId(AppidRequest appidRequest){
		return boundValueOperationsByAppId(redisTemplate, appidRequest);
	}

	private BoundValueOperations<String, AccessTokenInfo> boundValueOperationsByAppId(RedisTemplate<String, AccessTokenInfo> redisTemplate, AppidRequest appidRequest){
		String key = getAppidKey(appidRequest);
		BoundValueOperations<String, AccessTokenInfo> opt = (BoundValueOperations<String, AccessTokenInfo>)redisTemplate.boundValueOps(key);
		return opt;
	}
}
