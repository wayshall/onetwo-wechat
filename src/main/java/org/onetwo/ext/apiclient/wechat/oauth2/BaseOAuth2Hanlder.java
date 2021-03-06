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
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2Keys;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.slf4j.Logger;
import org.springframework.web.method.HandlerMethod;

/**
 * 微信公众号网页授权文档：
 * https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
 * 
 * @author wayshall
 * <br/>
 */
abstract public class BaseOAuth2Hanlder<U extends OAuth2User> {
	
	
	protected final Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	
	private WechatOAuth2UserRepository<U> wechatOAuth2UserRepository;
	
	
	abstract protected WechatConfig getWechatConfig(WechatOAuth2Context contex);
	/*protected WechatConfig getWechatConfig(WechatOAuth2Context contex) {
		WechatConfig wechatConfig = wechatConfigProvider.getWechatConfig(contex.getAppid());
		if (wechatConfig==null) {
			throw new WechatException("wechet config not found!").put("wechatConfigProvider", wechatConfigProvider)
																.put("appid", contex.getAppid());
		}
 		if (StringUtils.isNotBlank(contex.getAppid()) && !contex.getAppid().equals(wechatConfig.getAppid())) {
			throw new WechatException("config not found, error appid: " + contex.getAppid());
		}
		return wechatConfig;
	}*/

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
	
	/****
	 * code为空会跳转到微信oauth授权
	 * 授权成功归后，重新跳回这里，code不为空，根据code获取用户信息
	 * @author weishao zeng
	 * @param oauth2Request
	 * @param request
	 * @param response
	 * @return
	 */
	public U handleInController(OAuth2Request oauth2Request, HttpServletRequest request, HttpServletResponse response) {
		DataWechatOAuth2Context ctx = new DataWechatOAuth2Context(oauth2Request, request);
		return handleInController(ctx, response);
	}
	
	protected U handleInController(DataWechatOAuth2Context context, HttpServletResponse response) {
		context.setWechatConfig(getWechatConfig(context));
		this.checkWechatBrowser(context);
		
		U userInfo = null;
		if (StringUtils.isNotBlank(context.getCode())) {
			// 第二步：登录
			userInfo = loginByCode(context);
		} else if (StringUtils.isNotBlank(context.getState())) {
			//只有state参数，表示拒绝
			throw new WechatException(WechatClientErrors.OAUTH2_REJECTED);
		} else {
			// 第一步：跳转
			this.redirect(context, response);
		}
		
		return userInfo;
	}
	
	/***
	 * 从oauth服务器跳回后，验证state，并通过code获取用户信息
	 * @author weishao zeng
	 * @param context
	 * @return
	 */
	public U loginByCode(DataWechatOAuth2Context context) {
		if (context.getWechatConfig()==null) {
			context.setWechatConfig(getWechatConfig(context));
		}
		if(!wechatOAuth2UserRepository.checkOauth2State(context)){
			// 默认为 HttpRequestStoreService 实现，基于redis
			throw new WechatException(WechatClientErrors.OAUTH2_STATE_ERROR);
		}
		U userInfo = fetchOAuth2UserInfoFromServerWithCode(context);
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
		context.setWechatConfig(getWechatConfig(context));
		
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
			U userInfo = fetchOAuth2UserInfoFromServerWithCode(context);
			this.wechatOAuth2UserRepository.saveCurrentUser(context, userInfo, false);
			return true;
		} else {
			return handleWithoutCodeRequest(context, response, handler);
		}
	}
	
	/***
	 * 通过code从oauth服务器获取用户信息
	 * @author weishao zeng
	 * @param context
	 * @return
	 */
	abstract public U fetchOAuth2UserInfoFromServerWithCode(WechatOAuth2Context context);

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
	
	/****
	 * 
 * 微信公众号网页授权文档：
 * https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
	 * @author weishao zeng
	 * @param context
	 * @param response
	 */
	public void redirect(WechatOAuth2Context context, HttpServletResponse response) {
		try {
//			AuthorizeData authorizeData = getWechatAuthorizeData(request);
			String authorizeUrl = getAuthorizeUrl(context);
			boolean isDeubg = context.getWechatConfig()!=null && context.getWechatConfig().isDebug();
			if(isDeubg){
				logger.info("[wechat oauth2] redirect to authorizeUrl : {}", authorizeUrl);
			}
			response.sendRedirect(authorizeUrl);
		} catch (IOException e) {
			throw new WechatException("redirect error: " + e.getMessage(), e);
		}
	}
	
	protected String getAuthorizeUrl(WechatOAuth2Context context){
		WechatConfig wechatConfig = context.getWechatConfig();
		
		String redirectUrl = buildRedirectUrl(context);
		// trim一下，避免隐藏空格
		redirectUrl = StringUtils.trim(redirectUrl);
		
		String state = wechatOAuth2UserRepository.generateAndStoreOauth2State(context);
//		AuthorizeData authorize = wechatOauth2Client.createAuthorize(redirectUrl, state);
		AuthorizeData authorize = createAuthorize(wechatConfig.getAppid(), wechatConfig.getOauth2Scope(), redirectUrl, state);
		String authorizeUrl = authorize.toAuthorizeUrl();
		boolean isDeubg = context.getWechatConfig()!=null && context.getWechatConfig().isDebug();
		if (isDeubg) {
			logger.info("[wechat oauth2] authorizeUrl url: {}", authorizeUrl);
		}
		return authorizeUrl;
	}
	
	protected String buildRedirectUrl(WechatOAuth2Context context){
		boolean isDeubg = context.getWechatConfig()!=null && context.getWechatConfig().isDebug();
		String redirectUrl = context.getRedirectUrl();
		if (StringUtils.isBlank(redirectUrl)) {
			redirectUrl = context.getWechatConfig().getOauth2RedirectUri();
			// 从配置文件里获取的url需要encode一下
//			redirectUrl = LangUtils.encodeUrl(redirectUrl);
		}
		if (isDeubg) {
			logger.info("[wechat oauth2] wechat config redirect url: {}", redirectUrl);
		}
		//check redirectUri?
		if(StringUtils.isBlank(redirectUrl)){
//			throw new ApiClientException(WechatClientError.OAUTH2_REDIRECT_URL_BLANK);
			HttpServletRequest request = context.getRequest();
			redirectUrl = RequestUtils.buildFullRequestUrl(request.getScheme(), request.getServerName(), 80, request.getRequestURI(), request.getQueryString());
			if (isDeubg) {
				logger.info("[wechat oauth2] use default redirect url: {}", redirectUrl);
			}
		}
		redirectUrl = LangUtils.encodeUrl(redirectUrl);
		return redirectUrl;
	}
	
	static public AuthorizeData createAuthorize(String appid, String scope, String redirectUrl, String state){
		return AuthorizeData.builder()
							.appid(appid)
							.scope(scope)
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

}
