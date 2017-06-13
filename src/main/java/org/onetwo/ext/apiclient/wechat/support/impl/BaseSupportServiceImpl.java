package org.onetwo.ext.apiclient.wechat.support.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.md.Hashs;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.request.AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.request.ServerAuthRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.support.BaseSupportService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class BaseSupportServiceImpl implements BaseSupportService {
	
	@Autowired
	private WechatConfig wechatConfig;
	@Autowired
	private WechatServer wechatServer;
	
	@Override
	public AccessTokenResponse getAccessToken(){
		AccessTokenRequest request = AccessTokenRequest.builder()
														.grantType(wechatConfig.getGrantType())
														.appid(wechatConfig.getAppid())
														.secret(wechatConfig.getAppsecret())
														.build();
		AccessTokenResponse response = this.wechatServer.getAccessToken(request);
		return response;
	}
	
	@Override
	public String auth(ServerAuthRequest auth){
		return isValidRequest(auth)?auth.getEchostr():null;
	}
	
	private boolean isValidRequest(ServerAuthRequest auth){
		List<String> authItems = new ArrayList<>();
		authItems.add(wechatConfig.getToken());
		authItems.add(auth.getTimestamp());
		authItems.add(auth.getNonce());
		Collections.sort(authItems);
		String source = StringUtils.join(authItems, "");
		String sha1String = Hashs.SHA.hash(source);
		System.out.println("sha1:"+sha1String);
		System.out.println("signature:"+auth.getSignature());
		return sha1String.equalsIgnoreCase(auth.getSignature());
	}

}
