package org.onetwo.ext.apiclient.wechat.serve.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author wayshall
 * <br/>
 */
public class OAuth2SpringMvcInterceptor extends HandlerInterceptorAdapter {
	
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
