package org.onetwo.ext.apiclient.wechat.oauth2;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.data.AbstractDataResult.SimpleDataResult;
import org.onetwo.common.exception.BaseException;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.spring.copier.CopyUtils;
import org.onetwo.common.spring.mvc.utils.WebResultCreator;
import org.onetwo.common.web.utils.RequestUtils;
import org.onetwo.common.web.utils.ResponseUtils;
import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.oauth2.api.WechatOauth2Client;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2RefreshTokenRequest;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2UserInfoRequest;
import org.onetwo.ext.apiclient.wechat.oauth2.response.OAuth2AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.oauth2.response.OAuth2RefreshTokenResponse;
import org.onetwo.ext.apiclient.wechat.oauth2.response.OAuth2UserInfoResponse;
import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatSessionRepository;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2ClientKeys;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2Keys;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

/**
 * @author wayshall
 * <br/>
 */
public class WechatOAuth2Hanlder {
	
	
	private final Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WechatOauth2Client wechatOauth2Client;
	@Autowired
	private WechatSessionRepository sessionStoreService;
	@Autowired
	private WechatConfig wechatConfig;
	
	
	protected boolean isSsnUserInfoScope(){
		return Oauth2Keys.SCOPE_SNSAPI_USERINFO.equalsIgnoreCase(wechatConfig.getOauth2Scope());
	}
	
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
		if(isSsnUserInfoScope()){
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
	
	protected boolean refreshToken(HttpServletRequest request, OAuth2UserInfo userInfo){
		if(userInfo.isRefreshTokenExpired()){
			return false;
		}
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
		this.sessionStoreService.saveCurrentUser(RequestHoder.builder().request(request).build(), newUserInfo, true);
		return true;
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
		if(!RequestUtils.getBrowerMetaByAgent(request).isWechat()){
			if(wechatConfig.isOauth2ErrorInBrowser()){
				throw new BaseException(WechatClientErrors.OAUTH2_ERROR_IN_BROWSER);
			}/*else{
				return ;
			}*/
		}
		RequestHoder holder = RequestHoder.builder().request(request).build();
		Optional<OAuth2UserInfo> userInfoOpt = sessionStoreService.getCurrentUser(holder);
		if(userInfoOpt.isPresent()){
			OAuth2UserInfo userInfo = userInfoOpt.get();
			//token尚未过时或者刷新成功，直接返回
			if(!userInfo.isAccessTokenExpired()){
				return true;
			}else if(refreshToken(request, userInfo)){
				return true;
			}
		}
		
		String code = request.getParameter(Oauth2ClientKeys.PARAMS_CODE);
		if(StringUtils.isNotBlank(code)){
			String state = request.getParameter(Oauth2ClientKeys.PARAMS_STATE);
			if(!sessionStoreService.checkOauth2State(holder, state)){
				throw new WechatException(WechatClientErrors.OAUTH2_STATE_ERROR);
			}
			
			OAuth2AccessTokenRequest tokenRequest = OAuth2AccessTokenRequest.builder()
																			.code(code)
																			.build();
			OAuth2AccessTokenResponse tokenRespose = this.wechatOauth2Client.getAccessToken(tokenRequest);
			if(logger.isInfoEnabled()){
				logger.info("get access token : {}", tokenRespose);
			}
			
			OAuth2UserInfo userInfo = this.processUserInfo(request, tokenRespose);
			this.sessionStoreService.saveCurrentUser(holder, userInfo, false);
		}else{
			//如果是ajax请求，不跳转，返回错误信息
			if(RequestUtils.isAjaxRequest(request) || RequestUtils.isAjaxHandlerMethod(handler)){
				SimpleDataResult<?> result = WebResultCreator.creator()
								.error(WechatClientErrors.OAUTH2_NOT_AUTHORIZE)
								.buildResult();
				ResponseUtils.renderObjectAsJson(response, result);
				return false;
			}
			try {
				AuthorizeData authorizeData = getWechatAuthorizeData(request);
				String authorizeUrl = authorizeData.toAuthorizeUrl();
				if(logger.isInfoEnabled()){
					logger.info("redirect to authorizeUrl : {}", authorizeUrl);
				}
				response.sendRedirect(authorizeUrl);
			} catch (IOException e) {
				throw new RuntimeException("redirect error: " + e.getMessage(), e);
			}
		}
		return true;
	}
	
	protected AuthorizeData getWechatAuthorizeData(HttpServletRequest request){
		RequestHoder holder = RequestHoder.builder().request(request).build();
		String redirectUrl = buildRedirectUrl(request);
		String state = sessionStoreService.generateAndStoreOauth2State(holder);
		AuthorizeData authorize = wechatOauth2Client.createAuthorize(redirectUrl, state);
		return authorize;
	}
	
	protected String buildRedirectUrl(HttpServletRequest request){
		String url = RequestUtils.buildFullRequestUrl(request.getScheme(), request.getServerName(), 80, request.getRequestURI(), request.getQueryString());
		return url;
	}

}
