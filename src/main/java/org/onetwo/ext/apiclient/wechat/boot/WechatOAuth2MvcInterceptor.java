package org.onetwo.ext.apiclient.wechat.boot;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onetwo.boot.core.web.mvc.interceptor.MvcInterceptorAdapter;
import org.onetwo.ext.apiclient.wechat.oauth2.BaseOAuth2Hanlder;
import org.springframework.web.method.HandlerMethod;

/**
 * @author wayshall
 * <br/>
 */
public class WechatOAuth2MvcInterceptor extends MvcInterceptorAdapter {
	
	private BaseOAuth2Hanlder<?> oauth2Hanlder;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
		return oauth2Hanlder.preHandle(request, response, handler);
	}

	public void setOAuth2Hanlder(BaseOAuth2Hanlder<?> oauth2Hanlder) {
		this.oauth2Hanlder = oauth2Hanlder;
	}
	
}
