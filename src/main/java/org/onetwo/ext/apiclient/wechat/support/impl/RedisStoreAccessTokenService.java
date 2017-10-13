package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.concurrent.TimeUnit;

import org.onetwo.boot.module.redis.RedisLockRunner;
import org.onetwo.common.exception.BaseException;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.util.Assert;

/**
 * 基于redis
 * @author wayshall
 * <br/>
 */
public class RedisStoreAccessTokenService implements AccessTokenService, InitializingBean {

	@Autowired
	private WechatServer wechatServer;
	@Autowired
	private WechatConfig wechatConfig;
	/*@Autowired
	private JedisConnectionFactory jedisConnectionFactory;*/
	
	@Autowired
	private RedisLockRegistry redisLockRegistry;
	private RedisLockRunner redisLockRunner;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(redisTemplate, "redisTemplate not found");
		Assert.notNull(redisLockRegistry, "redisLockRegistry not found");
		Assert.notNull(wechatConfig);
		
		redisLockRunner = RedisLockRunner.builder()
										 .lockKey(WechatUtils.LOCK_KEY)
										 .errorHandler(e->new BaseException("refresh token error!", e))
										 .redisLockRegistry(redisLockRegistry)
										 .build();
	}

	public AccessTokenInfo getAccessToken() {
		AccessTokenInfo at = (AccessTokenInfo)redisTemplate.boundValueOps(WechatUtils.REDIS_ACCESS_TOKEN_KEY).get();
		if(at!=null && !at.isExpired()){
			return at;
		}
		return redisLockRunner.tryLock(()->obtainAccessToken(wechatConfig));
	}

	protected AccessTokenInfo obtainAccessToken(WechatConfig wechatConfig){
		//double check
		AccessTokenInfo at = (AccessTokenInfo)redisTemplate.boundValueOps(WechatUtils.REDIS_ACCESS_TOKEN_KEY).get();
		if(at!=null && !at.isExpired()){
			return at;
		}
		AccessTokenInfo token = WechatUtils.getAccessToken(wechatServer, wechatConfig);
		redisTemplate.boundValueOps(WechatUtils.REDIS_ACCESS_TOKEN_KEY).set(token, token.getExpiresIn(), TimeUnit.SECONDS);
		return token;
	}

	public WechatServer getWechatServer() {
		return wechatServer;
	}

	public WechatConfig getWechatConfig() {
		return wechatConfig;
	}

}
