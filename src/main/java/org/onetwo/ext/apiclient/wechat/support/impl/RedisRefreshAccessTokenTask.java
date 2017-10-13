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
	 * 当前时间距离上次更新时间的间隔，小于配置的时间间隔，则忽略任务
	 */
	@Value("${wechat.task.refreshToken.ignoreIntervalInMinutes:30}")
	private int ignoreIntervalInMinutes;
	
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
	 * 默认50分钟检查一次
	 * @author wayshall
	 */
	@Scheduled(cron="${wechat.task.refreshToken.cron:0 0/50 * * * *}")
	public void schedule(){
		log.info("start to refresh access token...");
		redisLockRunner.tryLock(()->refreshAccessToken(wechatConfig));
		log.info("refresh task finished!");
	}

	protected AccessTokenInfo refreshAccessToken(WechatConfig wechatConfig){
		AccessTokenInfo at = (AccessTokenInfo)redisTemplate.boundValueOps(WechatUtils.REDIS_ACCESS_TOKEN_KEY).get();
		if(at!=null && getIntervalInMinutesFromLastUpdate(at.getUpdateAt())<this.ignoreIntervalInMinutes){
			log.info("ignore refresh access token...");
			return at;
		}
		AccessTokenInfo token = WechatUtils.getAccessToken(wechatServer, wechatConfig);
		redisTemplate.boundValueOps(WechatUtils.REDIS_ACCESS_TOKEN_KEY).set(token, token.getExpiresIn(), TimeUnit.SECONDS);
		return token;
	}
	
	private long getIntervalInMinutesFromLastUpdate(long lastUpdateAt){
		long duration = System.currentTimeMillis() - lastUpdateAt;
		return TimeUnit.MILLISECONDS.toMinutes(duration);
	}
}
