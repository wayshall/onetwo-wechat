package org.onetwo.ext.apiclient.wechat.oauth2;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.data.DataResult;
import org.onetwo.common.exception.ErrorType;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.spring.mvc.utils.DataResults;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.common.web.utils.RequestUtils;
import org.onetwo.common.web.utils.ResponseUtils;
import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2Request;
import org.onetwo.ext.apiclient.wechat.serve.dto.WechatOAuth2Context;
import org.onetwo.ext.apiclient.wechat.serve.dto.WechatOAuth2Context.DataWechatOAuth2Context;
import org.onetwo.ext.apiclient.wechat.serve.dto.WechatOAuth2Context.RequestWechatOAuth2Context;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
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
	
	
	static public WechatConfig getWechatConfig(WechatConfigProvider wechatConfigProvider, WechatOAuth2Context contex) {
		WechatConfig wechatConfig = wechatConfigProvider.getWechatConfig(contex.getAppid());
		return wechatConfig;
	}

	protected boolean refreshToken(WechatOAuth2Context context, U userInfo){
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
	
	protected final boolean isOauth2ErrorInBrowser(WechatOAuth2Context contex) {
		return contex.getWechatConfig().isOauth2ErrorInBrowser();
	}
	

	protected void checkWechatBrowser(WechatOAuth2Context contex) {
		if(!RequestUtils.getBrowerMetaByAgent(contex.getRequest()).isWechat()){
			if(isOauth2ErrorInBrowser(contex)){
				throw new WechatException(WechatClientErrors.OAUTH2_ERROR_IN_BROWSER);
			}/*else{
				return ;
			}*/
		}
	}
	
	public U handleInController(OAuth2Request oauth2Request, HttpServletRequest request, HttpServletResponse response) {
		DataWechatOAuth2Context context = new DataWechatOAuth2Context(oauth2Request, request);
		context.setWechatConfig(getWechatConfig(wechatConfigProvider, context));
		this.checkWechatBrowser(context);
		
		U userInfo = null;
		if (StringUtils.isNotBlank(oauth2Request.getCode())) {
			if(!wechatOAuth2UserRepository.checkOauth2State(context)){
				throw new WechatException(WechatClientErrors.OAUTH2_STATE_ERROR);
			}
			userInfo = getOAuth2UserInfo(context);
		} else if (StringUtils.isNotBlank(oauth2Request.getState())) {
			//只有state参数，表示拒绝
			throw new WechatException(WechatClientErrors.OAUTH2_REJECTED);
		} else {
			// 跳转
			this.redirect(context, response);
		}
		
		return userInfo;
	}
	
	/****
	 * 拦截器处理逻辑
	 * @author weishao zeng
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
		RequestWechatOAuth2Context context = new RequestWechatOAuth2Context(request);
		context.setWechatConfig(getWechatConfig(wechatConfigProvider, context));
		
		this.checkWechatBrowser(context);
		
		Optional<U> userInfoOpt = wechatOAuth2UserRepository.getCurrentUser(context);
		if(userInfoOpt.isPresent()){
			U userInfo = userInfoOpt.get();
			//token尚未过时或者刷新成功，直接返回
			if(!userInfo.isAccessTokenExpired()){
				return true;
			}else if(refreshToken(context, userInfo)){
				return true;
			}
		}
		
//		return handleNotLoginRequest(request, response, handler);
		if (StringUtils.isNotBlank(context.getCode())) {
			if(!wechatOAuth2UserRepository.checkOauth2State(context)){
				throw new WechatException(WechatClientErrors.OAUTH2_STATE_ERROR);
			}
			U userInfo = getOAuth2UserInfo(context);
			this.wechatOAuth2UserRepository.saveCurrentUser(context, userInfo, false);
			return true;
		} else {
			return handleWithoutCodeRequest(context, response, handler);
		}
	}
	
	abstract protected U getOAuth2UserInfo(WechatOAuth2Context context);

	protected boolean handleWithoutCodeRequest(WechatOAuth2Context context, HttpServletResponse response, HandlerMethod handler) {
		ErrorType error = null;
		if (StringUtils.isNotBlank(context.getState())) {
			error = WechatClientErrors.OAUTH2_REJECTED;
		}
		//如果是ajax请求，不跳转，返回错误信息
		if(RequestUtils.isAjaxRequest(context.getRequest()) || RequestUtils.isAjaxHandlerMethod(handler)){
			DataResult<?> result = DataResults.error(error==null?WechatClientErrors.OAUTH2_NOT_AUTHORIZE:error).build();
			ResponseUtils.renderObjectAsJson(response, result);
			return false;
		}
		
		if (error!=null) {
			throw new WechatException(error);
		}
		this.redirect(context, response);
		return false;
	}
	
	protected void redirect(WechatOAuth2Context context, HttpServletResponse response) {
		try {
//			AuthorizeData authorizeData = getWechatAuthorizeData(request);
			String authorizeUrl = getAuthorizeUrl(context);
			if(logger.isInfoEnabled()){
				logger.info("redirect to authorizeUrl : {}", authorizeUrl);
			}
			response.sendRedirect(authorizeUrl);
		} catch (IOException e) {
			throw new WechatException("redirect error: " + e.getMessage(), e);
		}
	}
	
	protected String getAuthorizeUrl(WechatOAuth2Context context){
		WechatConfig wechatConfig = context.getWechatConfig();
		
		String redirectUrl = buildRedirectUrl(context);
		
		String state = wechatOAuth2UserRepository.generateAndStoreOauth2State(context);
//		AuthorizeData authorize = wechatOauth2Client.createAuthorize(redirectUrl, state);
		AuthorizeData authorize = createAuthorize(wechatConfig, redirectUrl, state);
		return authorize.toAuthorizeUrl();
	}
	
	protected String buildRedirectUrl(WechatOAuth2Context context){
		String redirectUrl = context.getWechatConfig().getOauth2RedirectUri();
		//check redirectUri?
		if(StringUtils.isBlank(redirectUrl)){
//			throw new ApiClientException(WechatClientError.OAUTH2_REDIRECT_URL_BLANK);
			HttpServletRequest request = context.getRequest();
			redirectUrl = RequestUtils.buildFullRequestUrl(request.getScheme(), request.getServerName(), 80, request.getRequestURI(), request.getQueryString());
		}
		redirectUrl = LangUtils.encodeUrl(redirectUrl);
		return redirectUrl;
	}
	
	static public AuthorizeData createAuthorize(WechatConfig wechatConfig, String redirectUrl, String state){
		return AuthorizeData.builder()
							.appid(wechatConfig.getAppid())
							.scope(wechatConfig.getOauth2Scope())
							.responseType(Oauth2Keys.RESPONSE_TYPE_CODE)
							.state(state)
							.redirectUri(redirectUrl)
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