package org.onetwo.ext.apiclient.work.message.request;

import java.awt.Image;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.onetwo.common.jackson.serializer.ListToStringSerializer.ListToVerticalJoinerStringSerializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
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
public class WorkMessageRequest {
	/***
	 * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向该企业应用的全部成员发送
	 */
	@JsonSerialize(using=ListToVerticalJoinerStringSerializer.class)
	private List<String> touser; //" : "UserID1|UserID2|UserID3",
	
	@JsonSerialize(using=ListToVerticalJoinerStringSerializer.class)
	private List<String> toparty; //" : "PartyID1|PartyID2",

	@JsonSerialize(using=ListToVerticalJoinerStringSerializer.class)
	private List<String> totag; //" : "TagID1 | TagID2",
	@NotNull
	final private WorkMessageType msgtype; //" : "text",
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
	
	public WorkMessageRequest(List<String> touser, List<String> toparty, List<String> totag, WorkMessageType msgtype,
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
		this(touser, toparty, totag, WorkMessageType.TEXT, agentid, safe);
		this.text = new TextData(text);
	}
	
	@Builder(builderClassName="ImageMessageBuilder", builderMethodName="imageBuilder")
	public WorkMessageRequest(List<String> touser, List<String> toparty, List<String> totag,
			Long agentid, String mediaId, int safe) {
		this(touser, toparty, totag, WorkMessageType.TEXT, agentid, safe);
		this.image = new ImageData(mediaId);
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

	@Builder(builderClassName="TextCardMessageBuilder", builderMethodName="textCardBuilder")
	public WorkMessageRequest(List<String> touser, List<String> toparty, List<String> totag,
			Long agentid, int safe, String title, String description, String url, String btntxt) {
		this(touser, toparty, totag, WorkMessageType.TEXTCARD, agentid, safe);
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

	public static enum WorkMessageType {
		TEXT,
		IMAGE,
		VOICE,
		VIDEO,
		FILE,
		/***
		 * 文本卡片消息
		 */
		TEXTCARD,
		/***
		 * 图文
		 */
		NEWS,
		/***
		 * mpnews类型的图文消息，跟普通的图文消息一致，唯一的差异是图文内容存储在企业微信。
多次发送mpnews，会被认为是不同的图文，阅读、点赞的统计会被分开计算。
		 */
		MPNEWS,
		MARKDOWN,
		MINIPROGRAM_NOTICE,
		TASKCARD;
		
		@JsonValue
		public String getValue() {
			return name().toLowerCase();
		}
		
	}
	   
}
