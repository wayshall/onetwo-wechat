package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.concurrent.locks.ReentrantLock;

import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基于内存
 * @author wayshall
 * <br/>
 */
public class MemoryAccessTokenService implements AccessTokenService {

	@Autowired
	private WechatServer wechatServer;
	@Autowired
	private WechatConfig wechatConfig;
	
	private volatile AccessTokenInfo accessToken;
	private ReentrantLock obtainAcessTokenLock = new ReentrantLock();
	
	public AccessTokenInfo getAccessToken() {
		AccessTokenInfo at = this.accessToken;
		if(at!=null && !at.isExpired()){
			return at;
		}
		return obtainAccessToken(wechatConfig);
	}

	protected AccessTokenInfo obtainAccessToken(WechatConfig wechatConfig){
		try {
			obtainAcessTokenLock.lock();
			if(accessToken!=null && !accessToken.isExpired()){
				return accessToken;
			}
			accessToken = WechatUtils.getAccessToken(wechatServer, wechatConfig);
			return accessToken;
		} finally{
			obtainAcessTokenLock.unlock();
		}
	}

	public WechatServer getWechatServer() {
		return wechatServer;
	}

	public WechatConfig getWechatConfig() {
		return wechatConfig;
	}

}
