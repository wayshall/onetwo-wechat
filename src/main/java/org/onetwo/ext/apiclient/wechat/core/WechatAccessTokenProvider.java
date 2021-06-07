package org.onetwo.ext.apiclient.wechat.core;

import java.util.Arrays;
import java.util.List;

import org.onetwo.common.apiclient.utils.ApiClientUtils;
import org.onetwo.common.exception.BaseException;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenType;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.utils.WechatUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class WechatAccessTokenProvider implements AccessTokenProvider {
	protected final Logger logger = ApiClientUtils.getApiclientlogger();
	@Autowired
	private WechatServer wechatServer;
	private WechatConfigProvider wechatConfigProvider;

	@Override
	public AccessTokenResponse getAccessToken(AppidRequest refreshTokenRequest) {
//		return wechatServer.getAccessToken(request);
		String appid = refreshTokenRequest.getAppid();
		WechatConfig wechatConfig = wechatConfigProvider.getWechatConfig(appid);
		WechatUtils.assertWechatConfigNotNull(wechatConfig, appid);
		if (appid==null || !appid.equals(wechatConfig.getAppid())) {
			String msg = String.format("appid error, ignore refresh, appid: %s, configuration appid: %s", appid, wechatConfig.getAppid());
			throw new BaseException(msg);
		}
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
																.appid(appid)
																.secret(wechatConfig.getAppsecret())
																.accessTokenType(refreshTokenRequest.getAccessTokenType())
															.build();
//		AccessTokenInfo tokenInfo = refreshAccessToken(request);
		return wechatServer.getAccessToken(request);
	}

	@Override
	public List<AccessTokenType> getAccessTokenTypes() {
		return Arrays.asList(AccessTokenTypes.WECHAT);
	}

	public void setWechatConfigProvider(WechatConfigProvider wechatConfigProvider) {
		this.wechatConfigProvider = wechatConfigProvider;
	}

}

