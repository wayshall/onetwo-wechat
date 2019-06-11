package org.onetwo.ext.apiclient.work.message.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.onetwo.common.jackson.serializer.ListToStringSerializer.ListToVerticalJoinerStringSerializer;
import org.onetwo.common.jackson.serializer.StringToListDerializer.VerticalSplitorToListDerializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@NoArgsConstructor
public class WorkMessageRequest {
	/***
	 * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向该企业应用的全部成员发送
	 */
	@JsonSerialize(using=ListToVerticalJoinerStringSerializer.class)
	@JsonDeserialize(using=VerticalSplitorToListDerializer.class)
	private List<String> touser; //" : "UserID1|UserID2|UserID3",
	
	@JsonSerialize(using=ListToVerticalJoinerStringSerializer.class)
	@JsonDeserialize(using=VerticalSplitorToListDerializer.class)
	private List<String> toparty; //" : "PartyID1|PartyID2",

	@JsonSerialize(using=ListToVerticalJoinerStringSerializer.class)
	@JsonDeserialize(using=VerticalSplitorToListDerializer.class)
	private List<String> totag; //" : "TagID1 | TagID2",
	@NotNull
	private WorkSendMessageType msgtype; //" : "text",
	/***
	 * 企业应用的id，整型。企业内部开发，可在应用的设置页面查看；第三方服务商，可通过接口 获取企业授权信息 获取该参数值
	 */
	@NotNull
	private Long agentid; //" : 1,
	/***
	 * 表示是否是保密消息，0表示可对外分享，1表示不能分享且内容显示水印，2表示仅限在企业内分享，默认为0；注意仅mpnews类型的消息支持safe值为2，其他消息类型不支持
	 */
	private int safe;	
	
	private TextData text;
	
	private ImageData image;
	
	private TextCardData textcard;
	private NewsData news;
	private MpnewsData mpnews;
	
	public WorkMessageRequest(List<String> touser, List<String> toparty, List<String> totag, WorkSendMessageType msgtype,
			Long agentid, int safe) {
		super();
		this.touser = touser;
		this.toparty = toparty;
		this.totag = totag;
		this.msgtype = msgtype;
		this.agentid = agentid;
		this.safe = safe;
	}
	
	@Builder(builderClassName="TextMessageBuilder", builderMethodName="textBuilder")
	public WorkMessageRequest(List<String> touser, List<String> toparty, List<String> totag,
			Long agentid, int safe, String text) {
		this(touser, toparty, totag, WorkSendMessageType.text, agentid, safe);
		this.text = new TextData(text);
	}
	
	@Builder(builderClassName="ImageMessageBuilder", builderMethodName="imageBuilder")
	public WorkMessageRequest(List<String> touser, List<String> toparty, List<String> totag,
			Long agentid, String mediaId, int safe) {
		this(touser, toparty, totag, WorkSendMessageType.text, agentid, safe);
		this.image = new ImageData(mediaId);
	}
	
	@Builder(builderClassName="NewsMessageBuilder", builderMethodName="newsBuilder")
	public WorkMessageRequest(List<String> touser, List<String> toparty, List<String> totag,
			Long agentid, List<NewsItemData> articles, int safe) {
		this(touser, toparty, totag, WorkSendMessageType.news, agentid, safe);
		this.news = new NewsData(articles);
	}
	
	@Builder(builderClassName="MpnewsMessageBuilder", builderMethodName="mpnewsBuilder")
	public WorkMessageRequest(List<String> touser, List<String> toparty, List<String> totag,
			Long agentid, int safe, List<MpnewsItemData> articles) {
		this(touser, toparty, totag, WorkSendMessageType.mpnews, agentid, safe);
		this.mpnews = new MpnewsData(articles);
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TextData {
		private String content;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ImageData {
		@JsonProperty("media_id")
		private String mediaId;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class NewsData {
		private List<NewsItemData> articles;
	}
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class NewsItemData {
		private String title;
		private String description;
		private String url;
		private String picurl;
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MpnewsData {
		private List<MpnewsItemData> articles;
	}
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class MpnewsItemData {
		private String title;
		/***
		 * 图文消息缩略图的media_id, 可以通过素材管理接口获得。此处thumb_media_id即上传接口返回的media_id
		 */
		@JsonProperty("thumb_media_id")
		private String thumbMediaId;
		private String author;
		/***
		 * 图文消息的内容，支持html标签，不超过666 K个字节
		 */
		private String content;
		/***
		 * 图文消息的描述，不超过512个字节，超过会自动截断
		 */
		private String digest;
		/***
		 * 图文消息点击“阅读原文”之后的页面链接
		 */
		@JsonProperty("content_source_url")
		private String contentSourceUrl;
	}

	@Builder(builderClassName="TextCardMessageBuilder", builderMethodName="textCardBuilder")
	public WorkMessageRequest(List<String> touser, List<String> toparty, List<String> totag,
			Long agentid, int safe, String title, String description, String url, String btntxt) {
		this(touser, toparty, totag, WorkSendMessageType.textcard, agentid, safe);
		this.textcard = new TextCardData(title, description, url, btntxt);
	}

	@Data
	@NoArgsConstructor
	public static class TextCardData {
		private String title;
		private String description;
		/***
		 * 点击后跳转的链接。
		 */
		private String url;
		/***
		 * 按钮文字。 默认为“详情”， 不超过4个文字，超过自动截断。
		 */
		private String btntxt;
		
		@Builder
		public TextCardData(String title, String description, String url, String btntxt) {
			super();
			this.title = title;
			this.description = description;
			this.url = url;
			this.btntxt = btntxt;
		}
	}

	public static enum WorkSendMessageType {
		text,
		image,
		voice,
		video,
		file,
		/***
		 * 文本卡片消息
		 */
		textcard,
		/***
		 * 图文
		 */
		news,
		/***
		 * mpnews类型的图文消息，跟普通的图文消息一致，唯一的差异是图文内容存储在企业微信。
多次发送mpnews，会被认为是不同的图文，阅读、点赞的统计会被分开计算。
		 */
		mpnews,
		markdown,
		miniprogram_notice,
		taskcard;
		
	}
	   
}
