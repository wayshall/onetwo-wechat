package org.onetwo.ext.apiclient.wechat.utils;

import org.onetwo.common.exception.ErrorType;
import org.springframework.http.MediaType;

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
	
	public static class MessageTypeParams {
		public static final String MSG_TYPE = "MsgType";
		public static final String TEXT = "MsgType=text";
		public static final String IMAGE = "MsgType=image";
		public static final String VOICE = "MsgType=voice";
		public static final String VIDEO = "MsgType=video";
		public static final String SHORTVIDEO = "MsgType=shortvideo";
		public static final String LOCATION = "MsgType=location";
		public static final String LINK = "MsgType=link";
		
	}
	public static enum MessageType {
		TEXT("文本消息"),
		IMAGE("图片消息"),
		VOICE("语音消息"),
		VIDEO("视频消息"),
		SHORTVIDEO("小视频消息"),
		LOCATION("地理位置消息"),
		LINK("链接消息");
		
		private String label;

		private MessageType(String label) {
			this.label = label;
		}
		
		public String getLabel() {
			return label;
		}

//		@JsonCreator
		public static MessageType of(String name){
			return valueOf(name.toUpperCase());
		}
		
	}
	
	public static class MediaTypeKeys {
		public static final String APPLICATION_XML_VALUE_UTF8 = MediaType.APPLICATION_XML_VALUE + ";charset=UTF-8";
		public static final String TEXT_XML_VALUE_UTF8 = MediaType.TEXT_XML_VALUE + ";charset=UTF-8";
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
