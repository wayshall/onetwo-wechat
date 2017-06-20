package org.onetwo.ext.apiclient.wechat.serve.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import org.onetwo.common.utils.FieldName;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.ReplyMessageType;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class ReplyMessage {
	
	@FieldName("ToUserName")
	private String toUserName;
	@FieldName("FromUserName")
	private String fromUserName;
	@FieldName("CreateTime")
	private long createTime;
	@FieldName("MsgType")
	private ReplyMessageType msgType;
	

	@Data
	@EqualsAndHashCode(callSuper=false)
	@Builder
	public static class TextReplyMessage extends ReplyMessage {
		@FieldName("Content")
		private String content;
	}

	@Data
	@EqualsAndHashCode(callSuper=false)
	@Builder
	public static class ImageReplyMessage extends ReplyMessage {
		@FieldName("Image")
		private Image image;

		@Value(staticConstructor="of")
		public static class Image {
			@FieldName("MediaId")
			private String mediaId;
		}
	}

	@Data
	@EqualsAndHashCode(callSuper=false)
	@Builder
	public static class VoiceReplyMessage extends ReplyMessage {
		@FieldName("Voice")
		private Voice voice;

		@Value(staticConstructor="of")
		public static class Voice {
			@FieldName("MediaId")
			private String mediaId;
		}
	}

	@Data
	@EqualsAndHashCode(callSuper=false)
	@Builder
	public static class VideoReplyMessage extends ReplyMessage {
		@FieldName("Video")
		private Video video;

		@Value(staticConstructor="of")
		public static class Video {
			@FieldName("MediaId")
			private String mediaId;
			@FieldName("Title")
			private String title;
			@FieldName("Description")
			private String description;
		}
	}

	@Data
	@EqualsAndHashCode(callSuper=false)
	@Builder
	public static class MusicReplyMessage extends ReplyMessage {
		@FieldName("Music")
		private Music music;

		@Value(staticConstructor="of")
		public static class Music {
			/****
			 * 音乐链接
			 */
			@FieldName("MusicUrl")
			private String musicUrl;
			/***
			 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
			 */
			@FieldName("HQMusicUrl")
			private String HQMusicUrl;
			/***
			 * 音乐标题
			 */
			@FieldName("Title")
			private String title;
			/***
			 * 音乐描述
			 */
			@FieldName("Description")
			private String description;
			/***
			 * 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
			 */
			@FieldName("ThumbMediaId")
			private String thumbMediaId;
		}
	}
	

	@Data
	@EqualsAndHashCode(callSuper=false)
	@Builder
	public static class NewsReplyMessage extends ReplyMessage {
		/***
		 * 图文消息个数，限制为8条以内
		 */
		@FieldName("ArticleCount")
		private Integer ArticleCount;
		/***
		 * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
		 */
		@FieldName("Articles")
		private List<Item> articles;

		@Value(staticConstructor="of")
		public static class Item {
			/***
			 * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
			 */
			@FieldName("PicUrl")
			private String picUrl;
			/***
			 * 点击图文消息跳转链接
			 */
			@FieldName("Url")
			private String url;	
			@FieldName("Title")
			private String title;
			@FieldName("Description")
			private String description;
		}
	}
}
