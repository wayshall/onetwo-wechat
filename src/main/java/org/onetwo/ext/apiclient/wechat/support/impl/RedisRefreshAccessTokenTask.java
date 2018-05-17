package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatAppInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.WechatConfigKeys;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.Assert;

import com.google.common.collect.Sets;

/**
 * 基于redis
 * @author wayshall
 * <br/>
 */
@Slf4j
public class RedisRefreshAccessTokenTask implements InitializingBean {
	private final Logger logger = JFishLoggerFactory.getLogger(this.getClass());

	private Set<WechatAppInfo> appInfos = Sets.newHashSet();
	@Autowired
	private AccessTokenService accessTokenService;
	@Autowired
	private WechatConfig wechatConfig;
	
	/****
	 * token有效时间，当前时间减去上次更新时间少于token有效时间时，忽略更新
	 */
	@Value(WechatConfigKeys.TASK_REFRESHTOKEN_TOKEN_EFFECTIVE_TIME)
	private int tokenEffectiveTimeInMinutes;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(accessTokenService, "accessTokenService not found");
		WechatAppInfo def = WechatAppInfo.builder()
										.appid(wechatConfig.getAppid())
										.appsecret(wechatConfig.getAppsecret())
										.build();
		if(!appInfos.contains(def)){
			appInfos.add(def);
		}
		this.wechatConfig.getApps().forEach((k, v)->{
			logger.info("add wechat app: {}", k);
			appInfos.add(v);
		});
	}

	/***
	 * @author wayshall
	 */
	@Scheduled(cron="${wechat.task.refreshToken.cron:0 0/10 * * * *}")
	public void scheduleRefreshTask(){
		log.info("start to refresh access token...");
		appInfos.forEach(appInfo->{
			refreshAccessToken(appInfo);
		});
		log.info("refresh task finished!");
	}

	protected AccessTokenInfo refreshAccessToken(WechatAppInfo appInfo){
		GetAccessTokenRequest request = WechatUtils.createGetAccessTokenRequest(appInfo);
		AccessTokenInfo at = this.accessTokenService.getAccessToken(request);
		if(at!=null && getIntervalInMinutesFromLastUpdate(at.getUpdateAt())<this.tokenEffectiveTimeInMinutes){
			log.info("ignore refresh access token...");
			return at;
		}
		if(logger.isInfoEnabled()){
			logger.info("==========>>> refresh access token from wechat server...");
		}
		at = accessTokenService.refreshAccessToken(request);
		return at;
	}
	
	private long getIntervalInMinutesFromLastUpdate(long lastUpdateAt){
		long duration = System.currentTimeMillis() - lastUpdateAt;
		return TimeUnit.MILLISECONDS.toMinutes(duration);
	}

	public void setAppInfos(Set<WechatAppInfo> appInfos) {
		this.appInfos = appInfos;
	}
	
}
