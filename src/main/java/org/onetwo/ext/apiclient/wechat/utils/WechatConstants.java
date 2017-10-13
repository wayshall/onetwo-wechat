package org.onetwo.ext.apiclient.wechat.utils;

import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage.ImageMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage.LinkMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage.LocationMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage.ShortvideoMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage.TextMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage.VideoMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage.VoiceMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReplyMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReplyMessage.ImageReplyMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReplyMessage.MusicReplyMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReplyMessage.NewsReplyMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReplyMessage.TextReplyMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReplyMessage.VideoReplyMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReplyMessage.VoiceReplyMessage;
import org.springframework.http.MediaType;

/**
 * @author wayshall
 * <br/>
 */
public abstract class WechatConstants {

	

	public static abstract class Oauth2ClientKeys {
		public static final String STORE_USER_INFO_KEY = "wechat_oauth2_userInfo";
		public static final String STORE_STATE_KEY = "wechat_oauth2_state";
		
		public static final String PARAMS_STATE = "state";
		public static final String PARAMS_CODE = "code";
	}
	
	public static final String PARAMS_ACCESS_TOKEN = "access_token";
	public static final String BODY_TO_USER_NAME = "ToUserName";
	public static final String BODY_ENCRYPT = "Encrypt";
	
	public static final String ENCRYPT_TYPE_AES = "aes";
	
	//grantType
	public static abstract class GrantTypeKeys {
		public static final String CLIENT_CREDENTIAL = "client_credential";
		public static final String AUTHORIZATION_CODE = "authorization_code";
		public static final String REFRESH_TOKEN = "refresh_token";
	}
	
	public static abstract class UrlConst {
		public static final String API_DOMAIN_URL = "https://api.weixin.qq.com";
		public static final String API_BASE_URL = API_DOMAIN_URL + "/cgi-bin";
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
	public static abstract class AccessTokenKeys {
		public static final String STORER_KEY = "wechat.accessToken.storer";
		public static final String STORER_REDIS_KEY = "redis";
		public static final String STORER_MEMORY_KEY = "memory";
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
		TEXT("文本消息", TextMessage.class),
		IMAGE("图片消息", ImageMessage.class),
		VOICE("语音消息", VoiceMessage.class),
		VIDEO("视频消息", VideoMessage.class),
		SHORTVIDEO("小视频消息", ShortvideoMessage.class),
		LOCATION("地理位置消息", LocationMessage.class),
		LINK("链接消息", LinkMessage.class);
		
		private String label;
		private Class<? extends ReceiveMessage> messageClass;

		private MessageType(String label, Class<? extends ReceiveMessage> messageClass) {
			this.label = label;
			this.messageClass = messageClass;
		}
		
		public String getLabel() {
			return label;
		}

		public Class<? extends ReceiveMessage> getMessageClass() {
			return messageClass;
		}
		public String getName(){
			return name().toLowerCase();
		}

		//		@JsonCreator
		public static MessageType of(String name){
			return valueOf(name.toUpperCase());
		}
		
		public static MessageType findByMessageClass(Class<? extends ReceiveMessage> messageClass){
			for(MessageType mt : values()){
				if(mt.messageClass==messageClass){
					return mt;
				}
			}
			throw new IllegalArgumentException("unknow message class: " + messageClass);
		}
		
	}
	

	public static enum ReplyMessageType {
		TEXT("文本消息", TextReplyMessage.class),
		IMAGE("图片消息", ImageReplyMessage.class),
		VOICE("语音消息", VoiceReplyMessage.class),
		VIDEO("视频消息", VideoReplyMessage.class),
		MUSIC("音乐消息", MusicReplyMessage.class),
		NEWS("图文消息", NewsReplyMessage.class);
		
		private String label;
		private Class<? extends ReplyMessage> messageClass;

		private ReplyMessageType(String label, Class<? extends ReplyMessage> messageClass) {
			this.label = label;
			this.messageClass = messageClass;
		}
		
		public String getLabel() {
			return label;
		}

		public Class<? extends ReplyMessage> getMessageClass() {
			return messageClass;
		}
		public String getName(){
			return name().toLowerCase();
		}

		//		@JsonCreator
		public static ReplyMessageType of(String name){
			return valueOf(name.toUpperCase());
		}
		
		public static ReplyMessageType findByMessageClass(Class<? extends ReplyMessage> messageClass){
			for(ReplyMessageType mt : values()){
				if(mt.messageClass==messageClass){
					return mt;
				}
			}
			throw new IllegalArgumentException("unknow message class: " + messageClass);
		}
		
	}
	
	public static class MediaTypeKeys {
		public static final String APPLICATION_XML_VALUE_UTF8 = MediaType.APPLICATION_XML_VALUE + ";charset=UTF-8";
		public static final String TEXT_XML_VALUE_UTF8 = MediaType.TEXT_XML_VALUE + ";charset=UTF-8";
	}
	

	public static enum AccessTokenStorers {
		MEMORY,
		REDIS
	}

}
