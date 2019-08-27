package org.onetwo.ext.apiclient.work.core;

import java.util.Arrays;
import java.util.List;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatConfigProvider;
import org.onetwo.ext.apiclient.work.basic.api.WorkTokenClient;
import org.onetwo.ext.apiclient.work.basic.api.WorkTokenClient.GetTokenRequest;
import org.onetwo.ext.apiclient.work.utils.WorkWechatUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 企业微信不同的应用都是使用corpid和对应应用的secret获取accesstoken，
	 * 但是accessTokenService是通过appid（这里是corrpid）作为可以来做缓存的，
	 * 这样缓存无法区分不同的应用对应不同的accessToken；
	 * 这里通过企业微信获取token的时候传入corpid:agentId的方式来区分，
	 * 所以获取token的时候要先去掉agentid
	 * 
 * @author weishao zeng
 * <br/>
 */
public class WorkWechatAccessTokenProvider implements AccessTokenProvider {
	
	
	@Autowired
	private WorkTokenClient workTokenClient;
	@Autowired
	private WechatConfigProvider wechatConfigProvider;

	@Override
	public AccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
		String corpid = WorkWechatUtils.splitCorpid(request.getAppid());
		GetTokenRequest getTokenRequest = GetTokenRequest.builder()
														.corpid(corpid)
														.corpsecret(request.getSecret())
														.build();
		return workTokenClient.getAccessToken(getTokenRequest);
	}
	
	@Override
	public List<AccessTokenTypes> getAccessTokenTypes() {
		return Arrays.asList(AccessTokenTypes.WORK_WECHAT, AccessTokenTypes.CONTACTS);
	}

}

