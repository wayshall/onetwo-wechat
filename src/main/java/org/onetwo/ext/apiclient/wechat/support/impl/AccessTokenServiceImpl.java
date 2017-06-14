package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.concurrent.locks.ReentrantLock;

import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.request.AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wayshall
 * <br/>
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

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
		return obtainAccessToken();
	}

	protected AccessTokenInfo obtainAccessToken(){
		try {
			obtainAcessTokenLock.lock();
			if(accessToken!=null && !accessToken.isExpired()){
				return accessToken;
			}
			AccessTokenRequest request = AccessTokenRequest.builder()
															.grantType(wechatConfig.getGrantType())
															.appid(wechatConfig.getAppid())
															.secret(wechatConfig.getAppsecret())
															.build();
			AccessTokenResponse response = this.wechatServer.getAccessToken(request);
			accessToken = AccessTokenInfo.builder()
										.accessToken(response.getAccessToken())
										.expiresIn(response.getExpiresIn())
										.build();
			return accessToken;
		} finally{
			obtainAcessTokenLock.unlock();
		}
	}

}
