package org.onetwo.ext.apiclient.wechat.utils;

import org.onetwo.common.exception.ErrorType;

/**
 * @author wayshall
 * <br/>
 */
public abstract class WechatConstants {
	
	public static final String PARAMS_ACCESS_TOKEN = "access_token";
	
	//grantType
	public static final String GT_CLIENT_CREDENTIAL = "client_credential";
	public static final String GT_AUTHORIZATION_CODE = "authorization_code ";
	
	public static abstract class UrlConst {
		public static final String API_BASE_URL = "https://api.weixin.qq.com/cgi-bin";
		public static final String OAUTH2_AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize";
		public static final String OAUTH2_AUTHORIZE_TEMPLATE = OAUTH2_AUTHORIZE+
																"?appid=${appid}&redirect_uri=${redirectUri}"
																+ "&response_type=${responseType}&scope=${scope}"
																+ "&state=${state}#wechat_redirect";
		
	}
	public static abstract class Oauth2Keys {
		public static final String SCOPE_SNSAPI_BASE = "snsapi_base";
		public static final String SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";
		public static final String RESPONSE_TYPE_CODE = "code";
		
	}
	
	/***
	 * 菜单按钮类型
	 * @author wayshall
	 *
	 */
	public static abstract class ButtonTypes {
		public static final String CLICK = "click";
		public static final String VIEW = "view";
		public static final String SCANCODE_PUSH = "scancode_push";
		public static final String SCANCODE_WAITMSG = "scancode_waitmsg";
		public static final String PIC_SYSPHOTO = "pic_sysphoto";
		public static final String PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
		public static final String PIC_WEIXIN = "pic_weixin";
		public static final String LOCATION_SELECT = "location_select";
		public static final String MEDIA_ID = "media_id";
		public static final String VIEW_LIMITED = "view_limited";
	}
	

	public static enum WechatClientError implements ErrorType {
		ACCESS_TOKEN_SERVICE_NOT_FOUND("AccessTokenService not found");
		
		private String errorMessage;

		private WechatClientError(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		@Override
		public String getErrorCode() {
			return name();
		}

		@Override
		public String getErrorMessage() {
			return errorMessage;
		}
		
	}
}
