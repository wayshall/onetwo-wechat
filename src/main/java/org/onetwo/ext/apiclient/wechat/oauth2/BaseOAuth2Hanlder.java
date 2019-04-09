package org.onetwo.ext.apiclient.wechat.oauth2;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.data.DataResult;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.spring.mvc.utils.DataResults;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.common.web.utils.RequestUtils;
import org.onetwo.common.web.utils.ResponseUtils;
import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.oauth2.response.OAuth2AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2ClientKeys;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2Keys;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.slf4j.Logger;
import org.springframework.web.method.HandlerMethod;

/**
 * @author wayshall
 * <br/>
 */
abstract public class BaseOAuth2Hanlder<U extends OAuth2User> {
	
	
	protected final Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	
	private WechatOAuth2UserRepository<U> wechatOAuth2UserRepository;
	private WechatConfigProvider wechatConfigProvider;
	
	
	protected boolean isSsnUserInfoScope(HttpServletRequest request){
		WechatConfig wechatConfig = getWechatConfig(request);
		return Oauth2Keys.SCOPE_SNSAPI_USERINFO.equalsIgnoreCase(wechatConfig.getOauth2Scope());
	}
	
	protected String getAppid(HttpServletRequest request) {
		return request.getParameter("appid");
	}
	
	protected WechatConfig getWechatConfig(HttpServletRequest request) {
		WechatConfig wechatConfig = wechatConfigProvider.getWechatConfig(getAppid(request));
		return wechatConfig;
	}
	/***
	 * 如果配置为userinfo，则去获取userinfo
	 * @author wayshall
	 * @param request
	 * @param userInfo
	 */
	abstract protected U processUserInfo(HttpServletRequest request, OAuth2AccessTokenResponse tokenRespose);

	protected boolean refreshToken(HttpServletRequest request, U userInfo){
		/*if(userInfo.isRefreshTokenExpired()){
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
		U newUserInfo = processUserInfo(request, accessReponse);
		this.sessionStoreService.saveCurrentUser(RequestHoder.builder().request(request).build(), newUserInfo, true);
		return true;*/
		return false;
	}
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
		if(!RequestUtils.getBrowerMetaByAgent(request).isWechat()){
			WechatConfig wechatConfig = getWechatConfig(request);
			if(wechatConfig.isOauth2ErrorInBrowser()){
				throw new WechatException(WechatClientErrors.OAUTH2_ERROR_IN_BROWSER);
			}/*else{
				return ;
			}*/
		}
		RequestHoder holder = RequestHoder.builder().request(request).build();
		Optional<U> userInfoOpt = wechatOAuth2UserRepository.getCurrentUser(holder);
		if(userInfoOpt.isPresent()){
			U userInfo = userInfoOpt.get();
			//token尚未过时或者刷新成功，直接返回
			if(!userInfo.isAccessTokenExpired()){
				return true;
			}else if(refreshToken(request, userInfo)){
				return true;
			}
		}
		
		return handleNotLoginRequest(request, response, handler);
	}
	
	/***
	 * 
	 * @author weishao zeng
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 */
	protected boolean handleNotLoginRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
		String code = request.getParameter(Oauth2ClientKeys.PARAMS_CODE);
		if(StringUtils.isNotBlank(code)){
			String state = request.getParameter(Oauth2ClientKeys.PARAMS_STATE);
			RequestHoder holder = RequestHoder.builder().request(request).build();
			if(!wechatOAuth2UserRepository.checkOauth2State(holder, state)){
				throw new WechatException(WechatClientErrors.OAUTH2_STATE_ERROR);
			}
			
			/*OAuth2AccessTokenRequest tokenRequest = OAuth2AccessTokenRequest.builder()
																			.code(code)
																			.build();
			OAuth2AccessTokenResponse tokenRespose = this.wechatOauth2Client.getAccessToken(tokenRequest);
			if(logger.isInfoEnabled()){
				logger.info("get access token : {}", tokenRespose);
			}
			
			U userInfo = this.processUserInfo(request, tokenRespose);*/
			U userInfo = getOAuth2UserInfo(request, code);
			this.wechatOAuth2UserRepository.saveCurrentUser(holder, userInfo, false);
			return true;
		}else{
			return handleWithoutCodeRequest(request, response, handler);
		}
	}
	
	abstract protected U getOAuth2UserInfo(HttpServletRequest request, String code);

	protected boolean handleWithoutCodeRequest(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
		//如果是ajax请求，不跳转，返回错误信息
		if(RequestUtils.isAjaxRequest(request) || RequestUtils.isAjaxHandlerMethod(handler)){
			DataResult<?> result = DataResults.error(WechatClientErrors.OAUTH2_NOT_AUTHORIZE).build();
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
			throw new WechatException("redirect error: " + e.getMessage(), e);
		}
		return true;
	}
	
	protected AuthorizeData getWechatAuthorizeData(HttpServletRequest request){
		RequestHoder holder = RequestHoder.builder().request(request).build();
		String redirectUrl = buildRedirectUrl(request);
		String state = wechatOAuth2UserRepository.generateAndStoreOauth2State(holder);
//		AuthorizeData authorize = wechatOauth2Client.createAuthorize(redirectUrl, state);
		AuthorizeData authorize = createAuthorize(getWechatConfig(request), redirectUrl, state);
		return authorize;
	}
	
	protected String buildRedirectUrl(HttpServletRequest request){
		String url = RequestUtils.buildFullRequestUrl(request.getScheme(), request.getServerName(), 80, request.getRequestURI(), request.getQueryString());
		return url;
	}
	
	static public AuthorizeData createAuthorize(WechatConfig wechatConfig, String redirectUrl, String state){
		String configRedirectUrl = wechatConfig.getOauth2RedirectUri();
		//check redirectUri?
		if(StringUtils.isBlank(configRedirectUrl)){
//			throw new ApiClientException(WechatClientError.OAUTH2_REDIRECT_URL_BLANK);
			configRedirectUrl = redirectUrl;
		}
		configRedirectUrl = LangUtils.encodeUrl(configRedirectUrl);
		return AuthorizeData.builder()
							.appid(wechatConfig.getAppid())
							.scope(wechatConfig.getOauth2Scope())
							.responseType(Oauth2Keys.RESPONSE_TYPE_CODE)
							.state(state)
							.redirectUri(configRedirectUrl)
							.build();
	}

	public WechatOAuth2UserRepository<U> getWechatOAuth2UserRepository() {
		return wechatOAuth2UserRepository;
	}

	public void setWechatOAuth2UserRepository(WechatOAuth2UserRepository<U> wechatOAuth2UserRepository) {
		this.wechatOAuth2UserRepository = wechatOAuth2UserRepository;
	}

	public WechatConfigProvider getWechatConfigProvider() {
		return wechatConfigProvider;
	}

	public void setWechatConfigProvider(WechatConfigProvider wechatConfigProvider) {
		this.wechatConfigProvider = wechatConfigProvider;
	}

}
