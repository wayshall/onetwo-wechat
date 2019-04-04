package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.onetwo.boot.module.redis.RedisUtils;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
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
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		this.redisTemplate = RedisUtils.createJsonValueRedisTemplate(jedisConnectionFactory);
		Assert.notNull(redisTemplate, "redisTemplate not found");
//		Assert.notNull(wechatConfig, "wechat config can not be null");
	}
	
	@Override
	protected String getStoreType() {
		return "redis";
	}


	@Override
	protected void removeByAppid(String appid) {
		String key = WechatUtils.getAccessTokenKey(appid, getSupportedClientType());
		this.redisTemplate.delete(key);
	}


	@Override
	protected void saveNewToken(AccessTokenInfo newToken) {
		BoundValueOperations<String, AccessTokenInfo> opt = boundValueOperationsByAppId(newToken.getAppid());
		long expired = newToken.getExpiresIn();
		opt.set(newToken, expired, TimeUnit.SECONDS);
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
	
	private BoundValueOperations<String, AccessTokenInfo> boundValueOperationsByAppId(String appid){
		return WechatUtils.boundValueOperationsByAppId(redisTemplate, appid, getSupportedClientType());
	}

}
