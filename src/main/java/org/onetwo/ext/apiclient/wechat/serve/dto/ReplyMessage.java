package org.onetwo.ext.apiclient.wechat.serve.dto;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import org.onetwo.ext.apiclient.wechat.serve.spi.Message;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.ReplyMessageType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class ReplyMessage implements Message {
	
	@JsonProperty("ToUserName")
	private String toUserName;
	@JsonProperty("FromUserName")
	private String fromUserName;
	@JsonProperty("CreateTime")
	private Long createTime;
	@JsonProperty("MsgType")
	private String msgType;
	

	public ReplyMessage(String toUserName, String fromUserName, Long createTime, String msgType) {
		super();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		if(createTime==null){
			this.createTime = new Date().getTime();
		}else{
			this.createTime = createTime;
		}
		this.msgType = msgType;
	}
	
	@JsonIgnore
	public FlowType getFlowType(){
		return FlowType.REPLY;
	}


	@Data
	@EqualsAndHashCode(callSuper=false)
	@JsonRootName("xml")
	public static class TextReplyMessage extends ReplyMessage {
		@JsonProperty("Content")
		private String content;

		@Builder
		public TextReplyMessage(String toUserName, String fromUserName, Long createTime, String msgType, String content) {
			super(toUserName, fromUserName, createTime, msgType);
			this.content = content;
			this.setMsgType(ReplyMessageType.findByMessageClass(getClass()).getName());
		}
	}

	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class ImageReplyMessage extends ReplyMessage {
		@JsonProperty("Image")
		private Image image;

		@Builder
		public ImageReplyMessage(String toUserName, String fromUserName, Long createTime, String msgType, Image image) {
			super(toUserName, fromUserName, createTime, msgType);
			this.image = image;
			this.setMsgType(ReplyMessageType.findByMessageClass(getClass()).getName());
		}

		@Value(staticConstructor="of")
		public static class Image {
			@JsonProperty("MediaId")
			private String mediaId;
		}
	}

	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class VoiceReplyMessage extends ReplyMessage {
		@JsonProperty("Voice")
		private Voice voice;

		@Builder
		public VoiceReplyMessage(String toUserName, String fromUserName, Long createTime, String msgType, Voice voice) {
			super(toUserName, fromUserName, createTime, msgType);
			this.voice = voice;
			this.setMsgType(ReplyMessageType.findByMessageClass(getClass()).getName());
		}

		@Value(staticConstructor="of")
		public static class Voice {
			@JsonProperty("MediaId")
			private String mediaId;
		}
	}

	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class VideoReplyMessage extends ReplyMessage {
		@JsonProperty("Video")
		private Video video;
		
		@Builder
		public VideoReplyMessage(String toUserName, String fromUserName, Long createTime, String msgType, Video video) {
			super(toUserName, fromUserName, createTime, msgType);
			this.video = video;
			this.setMsgType(ReplyMessageType.findByMessageClass(getClass()).getName());
		}

		@Value(staticConstructor="of")
		public static class Video {
			@JsonProperty("MediaId")
			private String mediaId;
			@JsonProperty("Title")
			private String title;
			@JsonProperty("Description")
			private String description;
		}
	}

	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class MusicReplyMessage extends ReplyMessage {
		@JsonProperty("Music")
		private Music music;
		
		@Builder
		public MusicReplyMessage(String toUserName, String fromUserName, Long createTime, String msgType, Music music) {
			super(toUserName, fromUserName, createTime, msgType);
			this.music = music;
			this.setMsgType(ReplyMessageType.findByMessageClass(getClass()).getName());
		}

		@Value(staticConstructor="of")
		public static class Music {
			/****
			 * 音乐链接
			 */
			@JsonProperty("MusicUrl")
			private String musicUrl;
			/***
			 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
			 */
			@JsonProperty("HQMusicUrl")
			private String HQMusicUrl;
			/***
			 * 音乐标题
			 */
			@JsonProperty("Title")
			private String title;
			/***
			 * 音乐描述
			 */
			@JsonProperty("Description")
			private String description;
			/***
			 * 缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id
			 */
			@JsonProperty("ThumbMediaId")
			private String thumbMediaId;
		}
	}
	

	@Data
	@EqualsAndHashCode(callSuper=false)
	public static class NewsReplyMessage extends ReplyMessage {
		/***
		 * 图文消息个数，限制为8条以内
		 */
		@JsonProperty("ArticleCount")
		private Integer ArticleCount;
		/***
		 * 多条图文消息信息，默认第一个item为大图,注意，如果图文数超过8，则将会无响应
		 */
		@JsonProperty("Articles")
		private List<Item> articles;
		
		
		@Builder
		public NewsReplyMessage(String toUserName, String fromUserName, Long createTime, String msgType, List<Item> articles) {
			super(toUserName, fromUserName, createTime, msgType);
			this.articles = articles;
			this.setMsgType(ReplyMessageType.findByMessageClass(getClass()).getName());
		}

		@Value(staticConstructor="of")
		public static class Item {
			/***
			 * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
			 */
			@JsonProperty("PicUrl")
			private String picUrl;
			/***
			 * 点击图文消息跳转链接
			 */
			@JsonProperty("Url")
			private String url;	
			@JsonProperty("Title")
			private String title;
			@JsonProperty("Description")
			private String description;
		}
	}
}
