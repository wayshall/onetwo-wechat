package org.onetwo.ext.apiclient.wechat.boot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onetwo.boot.core.web.mvc.interceptor.MvcInterceptorAdapter;
import org.onetwo.ext.apiclient.wechat.oauth2.WechatOAuth2Hanlder;
import org.springframework.web.method.HandlerMethod;

/**
 * @author wayshall
 * <br/>
 */
public class WechatOAuth2MvcInterceptor extends MvcInterceptorAdapter {
	
	private WechatOAuth2Hanlder oauth2Hanlder;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
		oauth2Hanlder.preHandle(request, response, handler);
		return true;
	}

	public void setOAuth2Hanlder(WechatOAuth2Hanlder oauth2Hanlder) {
		this.oauth2Hanlder = oauth2Hanlder;
	}
	
}
