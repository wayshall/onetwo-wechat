package org.onetwo.ext.apiclient.wechat.serve.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.onetwo.common.utils.FieldName;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class ReceiveMessage implements Message {
	
	@FieldName("ToUserName")
	private String toUserName;
	@FieldName("FromUserName")
	private String fromUserName;
	@FieldName("CreateTime")
	private long createTime;
	@FieldName("MsgId")
	private long msgId;
	@FieldName("MsgType")
	private String msgType;
	
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
		private String event;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	public static class MediaMessage extends ReceiveMessage {
		@FieldName("MediaId")
		private String mediaId;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TextMessage extends ReceiveMessage {
		@FieldName("Content")
		private String content;
	}

	@Data
	@EqualsAndHashCode(callSuper=true)
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ImageMessage extends MediaMessage {
		@FieldName("PicUrl")
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
		private String format;
		@FieldName("Recognition")
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
		private Double locationX;
		/***
		 * 地理位置经度
		 */
		@FieldName("Location_Y")
		private Double locationY;
		/***
		 * 地图缩放大小
		 */
		@FieldName("Scale")
		private Double scale;
		/***
		 * 地理位置信息
		 */
		@FieldName("Label")
		private String label;
	}


	@Data
	@EqualsAndHashCode(callSuper=true)
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class LinkMessage extends ReceiveMessage {
		@FieldName("Title")
		private String title;
		@FieldName("Description")
		private String description;
		@FieldName("Url")
		private String url;
	}
}
