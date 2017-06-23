package org.onetwo.ext.apiclient.wechat.serve.web;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.boot.core.web.mvc.interceptor.MvcInterceptorAdapter;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.web.utils.RequestUtils;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatOauth2Client;
import org.onetwo.ext.apiclient.wechat.basic.request.Oauth2AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
import org.onetwo.ext.apiclient.wechat.basic.response.Oauth2AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.basic.response.Oauth2UserInfoResponse;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;

/**
 * @author wayshall
 * <br/>
 */
public class WechatOAuth2Interceptor extends MvcInterceptorAdapter {
	
	public static final String USER_INFO_KEY = WechatConstants.WEB_USER_INFO_KEY;
	
	private final Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WechatOauth2Client wechatOauth2Client;
	
	protected Optional<Oauth2UserInfoResponse> getCurrentUserInfo(HttpServletRequest request){
		return Optional.ofNullable((Oauth2UserInfoResponse)request.getAttribute(USER_INFO_KEY));
	}
	
	protected void saveCurrentUserInfo(HttpServletRequest request, Oauth2UserInfoResponse userInfo){
		request.setAttribute(USER_INFO_KEY, userInfo);
	}
	
	@Override
	public void preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
		if(!RequestUtils.getBrowerMetaByAgent(request).isWechat()){
			return ;
		}
		Optional<Oauth2UserInfoResponse> userInfoOpt = getCurrentUserInfo(request);
		if(userInfoOpt.isPresent()){
			return ;
		}
		String code = request.getParameter("code");
		if(StringUtils.isNotBlank(code)){
			Oauth2AccessTokenRequest tokenRequest = Oauth2AccessTokenRequest.builder()
																		.code(code)
																		.build();
			Oauth2AccessTokenResponse tokenRespose = this.wechatOauth2Client.getAccessToken(tokenRequest);
			if(logger.isInfoEnabled()){
				logger.info("get access token : {}", tokenRespose);
			}
			if(tokenRespose.isSuccess()){
				Oauth2UserInfoResponse userInfo = this.wechatOauth2Client.getUserInfo(tokenRespose.getOpenid());
				this.saveCurrentUserInfo(request, userInfo);
			}
		}else{
			String state = UUID.randomUUID().toString();
			AuthorizeData authorize = wechatOauth2Client.createAuthorize(state);
			try {
				String redirectUrl = authorize.toAuthorizeUrl();
				if(logger.isInfoEnabled()){
					logger.info("redirect to : {}", redirectUrl);
				}
				response.sendRedirect(redirectUrl);
			} catch (IOException e) {
				throw new RuntimeException("redirect error: " + e.getMessage(), e);
			}
		}
	}

}
