package org.onetwo.ext.apiclient.wechat.serve.dto;

import org.onetwo.common.annotation.FieldName;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message;
import org.onetwo.ext.apiclient.wechat.serve.spi.Tenantable;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author wayshall
 * <br/>
 */
@Data
@JacksonXmlRootElement(localName="xml")
public class ReceiveMessage implements Message, Tenantable {
	
	@FieldName("ToUserName")
	@JacksonXmlProperty(localName="ToUserName")
	private String toUserName;
	
	@FieldName("FromUserName")
	@JacksonXmlProperty(localName="FromUserName")
	private String fromUserName;
	
	@FieldName("CreateTime")
	@JacksonXmlProperty(localName="CreateTime")
	private Long createTime;
	
	@FieldName("MsgId")
	@JacksonXmlProperty(localName="MsgId")
	private Long msgId;
	
	@FieldName("MsgType")
	@JacksonXmlProperty(localName="MsgType")
	private String msgType;
	
	private String clientId;
	
	public FlowType getFlowType(){
		return FlowType.RECEIVE;
	}
	
	/***
	 * 关注/取消关注事件
	 * 详见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140454
	 * @author wayshall
	 *
	 */
	@Data
	@EqualsAndHashCode(callSuper=true)
	public static class EventMessage extends ReceiveMessage {
		@FieldName("Event")
		@JacksonXmlProperty(localName="Event")
		private String event;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	public static class MediaMessage extends ReceiveMessage {
		@FieldName("MediaId")
		@JacksonXmlProperty(localName="MediaId")
		private String mediaId;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TextMessage extends ReceiveMessage {
		@FieldName("Content")
		@JacksonXmlProperty(localName="Content")
		private String content;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ImageMessage extends MediaMessage {
		@FieldName("PicUrl")
		@JacksonXmlProperty(localName="PicUrl")
		private String picUrl;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class VoiceMessage extends MediaMessage {
		/***
		 * 语音格式，如amr，speex等
		 */
		@FieldName("Format")
		@JacksonXmlProperty(localName="Format")
		private String format;
		
		@FieldName("Recognition")
		@JacksonXmlProperty(localName="Recognition")
		private String recognition;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class VideoMessage extends MediaMessage {
		/***
		 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
		 */
		@FieldName("ThumbMediaId")
		@JacksonXmlProperty(localName="ThumbMediaId")
		private String thumbMediaId;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ShortvideoMessage extends MediaMessage {
		/***
		 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
		 */
		@FieldName("ThumbMediaId")
		@JacksonXmlProperty(localName="ThumbMediaId")
		private String thumbMediaId;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LocationMessage extends ReceiveMessage {
		/**
		 * 地理位置维度
		 */
		@FieldName("Location_X")
		@JacksonXmlProperty(localName="Location_X")
		private Double locationX;
		/***
		 * 地理位置经度
		 */
		@FieldName("Location_Y")
		@JacksonXmlProperty(localName="Location_Y")
		private Double locationY;
		/***
		 * 地图缩放大小
		 */
		@FieldName("Scale")
		@JacksonXmlProperty(localName="Scale")
		private Double scale;
		/***
		 * 地理位置信息
		 */
		@FieldName("Label")
		@JacksonXmlProperty(localName="Label")
		private String label;
	}


	@Data
	@EqualsAndHashCode(callSuper=true)
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LinkMessage extends ReceiveMessage {
		@FieldName("Title")
		@JacksonXmlProperty(localName="Title")
		private String title;
		
		@FieldName("Description")
		@JacksonXmlProperty(localName="Description")
		private String description;
		
		@FieldName("Url")
		@JacksonXmlProperty(localName="Url")
		private String url;
	}
}
