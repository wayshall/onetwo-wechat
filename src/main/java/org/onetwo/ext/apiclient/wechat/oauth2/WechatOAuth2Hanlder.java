package org.onetwo.ext.apiclient.wechat.oauth2;

import org.onetwo.common.spring.copier.CopyUtils;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.core.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.oauth2.api.WechatOauth2Client;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2RefreshTokenRequest;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2UserInfoRequest;
import org.onetwo.ext.apiclient.wechat.oauth2.response.OAuth2AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.oauth2.response.OAuth2RefreshTokenResponse;
import org.onetwo.ext.apiclient.wechat.oauth2.response.OAuth2UserInfoResponse;
import org.onetwo.ext.apiclient.wechat.serve.dto.WechatOAuth2Context;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class WechatOAuth2Hanlder extends BaseOAuth2Hanlder<OAuth2UserInfo> {
	
//	@Autowired
	protected WechatOauth2Client wechatOauth2Client;
	@Autowired
	private WechatConfigProvider wechatConfigProvider;
	
	public WechatConfig getWechatConfig(WechatOAuth2Context contex) {
		WechatConfig wechatConfig = wechatConfigProvider.getWechatConfig(contex.getAppid());
		if (wechatConfig==null) {
			throw new WechatException("wechet config not found!").put("wechatConfigProvider", wechatConfigProvider)
																.put("appid", contex.getAppid());
		}
 		if (StringUtils.isNotBlank(contex.getAppid()) && !contex.getAppid().equals(wechatConfig.getAppid())) {
			throw new WechatException("config not found, error appid: " + contex.getAppid());
		}
		return wechatConfig;
	}
	
	
	/***
	 * 如果配置为userinfo，则去获取userinfo
	 * @author wayshall
	 * @param request
	 * @param userInfo
	 */
	protected OAuth2UserInfo processUserInfo(WechatOAuth2Context context, OAuth2AccessTokenResponse tokenRespose){
		OAuth2UserInfo userInfo = CopyUtils.copy(OAuth2UserInfo.class, tokenRespose);
		userInfo.setAccessAt(System.currentTimeMillis());
		userInfo.setRefreshAt(userInfo.getAccessAt());
		if(context.isSsnUserInfoScope()){
			OAuth2UserInfoRequest userInfoRequest = OAuth2UserInfoRequest.builder()
																		.accessToken(tokenRespose.getAccessToken())
																		.openid(tokenRespose.getOpenid())
																		.build();
			
			OAuth2UserInfoResponse userInfoResponse = this.wechatOauth2Client.getUserInfo(userInfoRequest);
			CopyUtils.copier()
					.ignoreNullValue()
					.ignoreBlankString()
					.from(userInfoResponse)
					.to(userInfo);
		}
		return userInfo;
	}
	
	
	@Override
	protected OAuth2UserInfo getOAuth2UserInfo(WechatOAuth2Context context) {
		OAuth2AccessTokenRequest tokenRequest = OAuth2AccessTokenRequest.builder()
																		.code(context.getCode())
																		.appid(context.getWechatConfig().getAppid())
																		.secret(context.getWechatConfig().getAppsecret())
																		.build();
		OAuth2AccessTokenResponse tokenRespose = this.wechatOauth2Client.getAccessToken(tokenRequest);
		if(logger.isInfoEnabled()){
		logger.info("get access token : {}", tokenRespose);
		}
		
		OAuth2UserInfo userInfo = this.processUserInfo(context, tokenRespose);
		return userInfo;
	}

	protected boolean refreshToken(WechatOAuth2Context context, OAuth2UserInfo userInfo){
		if(userInfo.isRefreshToken()){
			return false;
		}
		WechatConfig wechatConfig = context.getWechatConfig();
		OAuth2RefreshTokenRequest refreshRequest = OAuth2RefreshTokenRequest.builder()
																	.appid(wechatConfig.getAppid())
																	.refreshToken(userInfo.getRefreshToken())
																	.build();
		OAuth2RefreshTokenResponse response = wechatOauth2Client.refreshToken(refreshRequest);
		if(logger.isInfoEnabled()){
			logger.info("refresh token response: {}", response);
		}
		OAuth2AccessTokenResponse accessReponse = CopyUtils.copy(OAuth2AccessTokenResponse.class, response);
		OAuth2UserInfo newUserInfo = processUserInfo(context, accessReponse);
		this.getWechatOAuth2UserRepository().saveCurrentUser(context, newUserInfo, true);
		return true;
	}

	public void setWechatOauth2Client(WechatOauth2Client wechatOauth2Client) {
		this.wechatOauth2Client = wechatOauth2Client;
	}
}
