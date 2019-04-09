package org.onetwo.ext.apiclient.wechat.oauth2;

import javax.servlet.http.HttpServletRequest;

import org.onetwo.common.spring.copier.CopyUtils;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.oauth2.api.WechatOauth2Client;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2RefreshTokenRequest;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2UserInfoRequest;
import org.onetwo.ext.apiclient.wechat.oauth2.response.OAuth2AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.oauth2.response.OAuth2RefreshTokenResponse;
import org.onetwo.ext.apiclient.wechat.oauth2.response.OAuth2UserInfoResponse;
import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;

/**
 * @author wayshall
 * <br/>
 */
public class WechatOAuth2Hanlder extends BaseOAuth2Hanlder<OAuth2UserInfo> {
	
//	@Autowired
	protected WechatOauth2Client wechatOauth2Client;
	
	
	/***
	 * 如果配置为userinfo，则去获取userinfo
	 * @author wayshall
	 * @param request
	 * @param userInfo
	 */
	protected OAuth2UserInfo processUserInfo(HttpServletRequest request, OAuth2AccessTokenResponse tokenRespose){
		OAuth2UserInfo userInfo = CopyUtils.copy(OAuth2UserInfo.class, tokenRespose);
		userInfo.setAccessAt(System.currentTimeMillis());
		userInfo.setRefreshAt(userInfo.getAccessAt());
		if(isSsnUserInfoScope(request)){
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
	protected OAuth2UserInfo getOAuth2UserInfo(HttpServletRequest request, String code) {
		OAuth2AccessTokenRequest tokenRequest = OAuth2AccessTokenRequest.builder()
																		.code(code)
																		.build();
		OAuth2AccessTokenResponse tokenRespose = this.wechatOauth2Client.getAccessToken(tokenRequest);
		if(logger.isInfoEnabled()){
		logger.info("get access token : {}", tokenRespose);
		}
		
		OAuth2UserInfo userInfo = this.processUserInfo(request, tokenRespose);
		return userInfo;
	}

	protected boolean refreshToken(HttpServletRequest request, OAuth2UserInfo userInfo){
		if(userInfo.isRefreshToken()){
			return false;
		}
		WechatConfig wechatConfig = getWechatConfig(request);
		OAuth2RefreshTokenRequest refreshRequest = OAuth2RefreshTokenRequest.builder()
																	.appid(wechatConfig.getAppid())
																	.refreshToken(userInfo.getRefreshToken())
																	.build();
		OAuth2RefreshTokenResponse response = wechatOauth2Client.refreshToken(refreshRequest);
		if(logger.isInfoEnabled()){
			logger.info("refresh token response: {}", response);
		}
		OAuth2AccessTokenResponse accessReponse = CopyUtils.copy(OAuth2AccessTokenResponse.class, response);
		OAuth2UserInfo newUserInfo = processUserInfo(request, accessReponse);
		this.getWechatOAuth2UserRepository().saveCurrentUser(RequestHoder.builder().request(request).build(), newUserInfo, true);
		return true;
	}

	public void setWechatOauth2Client(WechatOauth2Client wechatOauth2Client) {
		this.wechatOauth2Client = wechatOauth2Client;
	}
}
