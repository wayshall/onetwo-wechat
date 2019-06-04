package org.onetwo.ext.apiclient.wechat.serve.dto;

import javax.servlet.http.HttpServletRequest;

import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2Request;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2ClientKeys;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2Keys;

import lombok.Setter;

/**
 * @author wayshall
 * <br/>
 */
public interface WechatOAuth2Context {
	
	String getAppid();
	

	String getCode();
	
	String getState();
	
	HttpServletRequest getRequest();
	WechatConfig getWechatConfig();
	

	default boolean isSsnUserInfoScope(){
		WechatConfig wechatConfig = getWechatConfig();
		return Oauth2Keys.SCOPE_SNSAPI_USERINFO.equalsIgnoreCase(wechatConfig.getOauth2Scope());
	}
	

	public class RequestWechatOAuth2Context implements WechatOAuth2Context {
		private HttpServletRequest request;
		@Setter
		private WechatConfig wechatConfig;
		
		public RequestWechatOAuth2Context(HttpServletRequest request) {
			super();
			this.request = request;
		}
		

		public String getAppid() {
			return request.getParameter("appid");
		}
		
		public String getCode() {
			return request.getParameter(Oauth2ClientKeys.PARAMS_CODE);
		}
		
		public String getState() {
			return request.getParameter(Oauth2ClientKeys.PARAMS_STATE);
		}


		@Override
		public HttpServletRequest getRequest() {
			return request;
		}


		@Override
		public WechatConfig getWechatConfig() {
			return wechatConfig;
		}
		
		
	}
	

	public class DataWechatOAuth2Context implements WechatOAuth2Context {
		private HttpServletRequest request;
		private OAuth2Request oauth2Request;
		@Setter
		private WechatConfig wechatConfig;
		
		public DataWechatOAuth2Context(OAuth2Request oauth2Request, HttpServletRequest request) {
			super();
			this.request = request;
			this.oauth2Request = oauth2Request;
		}
		

		public String getAppid() {
			return oauth2Request.getAppid();
		}
		
		public String getCode() {
			return oauth2Request.getCode();
		}
		
		public String getState() {
			return oauth2Request.getState();
		}


		@Override
		public HttpServletRequest getRequest() {
			return request;
		}
		@Override
		public WechatConfig getWechatConfig() {
			return wechatConfig;
		}
		
	}

}
