package org.onetwo.ext.apiclient.wechat.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author wayshall
 * <br/>
 */
public class OAuth2SpringMvcInterceptor implements HandlerInterceptor {
	
	@Autowired
	private WechatOAuth2Hanlder wechatOAuth2Hanlder;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod method = getHandlerMethod(handler);
		if(method==null){
			return false;
		}
		return wechatOAuth2Hanlder.preHandle(request, response, method);
	}
	

	protected HandlerMethod getHandlerMethod(Object handler){
		if(handler instanceof HandlerMethod){
			return (HandlerMethod)handler;
		}
		return null;
	}
}
