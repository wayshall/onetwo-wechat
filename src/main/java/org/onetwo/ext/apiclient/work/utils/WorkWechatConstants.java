package org.onetwo.ext.apiclient.work.utils;

import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message.ReceiveMessageType;
import org.onetwo.ext.apiclient.work.core.WorkWechatConfig;
import org.onetwo.ext.apiclient.work.serve.vo.message.ImageWorkMessage;
import org.onetwo.ext.apiclient.work.serve.vo.message.LinkWorkMessage;
import org.onetwo.ext.apiclient.work.serve.vo.message.LocationWorkMessage;
import org.onetwo.ext.apiclient.work.serve.vo.message.TextWorkMessage;
import org.onetwo.ext.apiclient.work.serve.vo.message.VideoWorkMessage;
import org.onetwo.ext.apiclient.work.serve.vo.message.VoiceWorkMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wayshall
 * <br/>
 */
public abstract class WorkWechatConstants {

	public static abstract class LockerKeys {
		public static final String JSAPI_TICKET = "LOCKER:jsApiTicket:";
	}

	public static abstract class WorkWechatConfigKeys {
		public static final String STORER_KEY = WorkWechatConfig.CONFIG_PREFIX + ".accessToken.storer";
	}

	
	public static abstract class WorkUrlConst {
		public static final String API_DOMAIN_URL = "https://qyapi.weixin.qq.com";
		public static final String API_BASE_URL = API_DOMAIN_URL + "/cgi-bin";
		
	}

	abstract public static class ContactChangeTypes {
		public static final String CREATE_USER = "create_user";
		public static final String UPDATE_USER = "update_user";
		public static final String DELETE_USER = "delete_user";
		public static final String CREATE_PARTY = "create_party";
		public static final String UPDATE_PARTY = "update_party";
		public static final String DELETE_PARTY = "delete_party";
		public static final String UPDATE_TAG = "update_tag";
	}

	abstract public static class WorkReveiveMessageTypeNames {
		public static final String MQ_MESSAGE_TAG = "work_message";
		public static final String TEXT = MQ_MESSAGE_TAG + "_text";
		public static final String IMAGE = MQ_MESSAGE_TAG + "_image";
		public static final String VOICE = MQ_MESSAGE_TAG + "_voice";
		public static final String VIDEO = MQ_MESSAGE_TAG + "_video";
		public static final String LOCATION = MQ_MESSAGE_TAG + "_location";
		public static final String LINK = MQ_MESSAGE_TAG + "_link";
	}
	
	@AllArgsConstructor
	public static enum WorkReveiveMessageType implements ReceiveMessageType {
		TEXT(WorkReveiveMessageTypeNames.TEXT, "文本消息", TextWorkMessage.class),
		IMAGE(WorkReveiveMessageTypeNames.IMAGE, "图片消息", ImageWorkMessage.class),
		VOICE(WorkReveiveMessageTypeNames.VOICE, "语音消息", VoiceWorkMessage.class),
		VIDEO(WorkReveiveMessageTypeNames.VIDEO, "视频消息", VideoWorkMessage.class),
		LOCATION(WorkReveiveMessageTypeNames.LOCATION, "位置消息", LocationWorkMessage.class),
		LINK(WorkReveiveMessageTypeNames.LINK, "链接消息", LinkWorkMessage.class),
		;

		@Getter
		final private String name;
		@Getter
		final private String label;
		@Getter
		final private Class<? extends ReceiveMessage> messageClass;
	}
	
	/*@AllArgsConstructor
	public static enum ContactChangeTypes implements ReceiveMessageType{
		
		CREATE_USER("新增成员事件", ContactCreateUserMessage.class),
//		DELETE_USER("删除成员事件"),
//		UPDATE_USER("更新成员事件")
		;
		
		@Getter
		private String label;
		@Getter
		private Class<? extends ReceiveMessage> messageClass;
		
		public String getName() {
			return name().toLowerCase();
		}

	}*/
}
