package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.onetwo.boot.module.redis.RedisLockRunner;
import org.onetwo.common.exception.BaseException;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;

/**
 * 基于redis
 * @author wayshall
 * <br/>
 */
@Slf4j
public class RedisRefreshAccessTokenTask implements InitializingBean {

	@Autowired
	private WechatServer wechatServer;
	@Autowired
	private WechatConfig wechatConfig;
	
	@Autowired
	private RedisLockRegistry redisLockRegistry;
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private RedisLockRunner redisLockRunner;
	/****
	 * token有效时间，当前时间减去上次更新时间少于token有效时间时，忽略更新
	 */
	@Value("${wechat.task.refreshToken.tokenEffectiveTimeInMinutes:110}")
	private int tokenEffectiveTimeInMinutes;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(redisTemplate, "redisTemplate not found");
		Assert.notNull(redisLockRegistry, "redisLockRegistry not found");
		Assert.notNull(redisLockRegistry);
		Assert.notNull(wechatConfig);

		redisLockRunner = RedisLockRunner.builder()
										 .lockKey(WechatUtils.LOCK_KEY)
										 .errorHandler(e->new BaseException("refresh token task error!", e))
										 .redisLockRegistry(redisLockRegistry)
										 .build();
	}

	/***
	 * @author wayshall
	 */
	@Scheduled(cron="${wechat.task.refreshToken.cron:0 0/20 * * * *}")
	public void scheduleRefreshTask(){
		log.info("start to refresh access token...");
		AccessTokenInfo at = (AccessTokenInfo)redisTemplate.boundValueOps(WechatUtils.REDIS_ACCESS_TOKEN_KEY).get();
		if(at!=null && getIntervalInMinutesFromLastUpdate(at.getUpdateAt())<this.tokenEffectiveTimeInMinutes){
			log.info("ignore refresh access token...");
			return ;
		}
		redisLockRunner.tryLock(()->refreshAccessToken(wechatConfig));
		log.info("refresh task finished!");
	}

	protected AccessTokenInfo refreshAccessToken(WechatConfig wechatConfig){
		/*AccessTokenInfo at = (AccessTokenInfo)redisTemplate.boundValueOps(WechatUtils.REDIS_ACCESS_TOKEN_KEY).get();
		if(at!=null && getIntervalInMinutesFromLastUpdate(at.getUpdateAt())<this.ignoreIntervalInMinutes){
			log.info("ignore refresh access token...");
			return at;
		}*/
		AccessTokenInfo token = WechatUtils.getAccessToken(wechatServer, wechatConfig);
		redisTemplate.boundValueOps(WechatUtils.REDIS_ACCESS_TOKEN_KEY).set(token, token.getExpiresIn(), TimeUnit.SECONDS);
		return token;
	}
	
	private long getIntervalInMinutesFromLastUpdate(long lastUpdateAt){
		long duration = System.currentTimeMillis() - lastUpdateAt;
		return TimeUnit.MILLISECONDS.toMinutes(duration);
	}
}
