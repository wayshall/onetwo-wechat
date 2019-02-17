package org.onetwo.ext.apiclient.wechat.message.request;

import java.util.LinkedHashMap;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://mp.weixin.qq.com/wiki?action=doc&id=mp1433751277#5
 * 
 * @author weishao zeng
 * <br/>
 */
@Data
public class MpTemplateMessge {
	@JsonProperty("template_id")
	private String templateId;
	private String url;
	/***
	 * 公众号模板消息所要跳转的小程序，小程序的必须与公众号具有绑定关系
	 */
	private MiniprogramData miniprogram;

	@NotNull
	private LinkedHashMap<String, MpTemplateMessgeData> data;
	
	@Data
	public static class MiniprogramData {
		private String appid;
		private String pagepath;
	}

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	protected static class MpTemplateMessgeData {
		private String value;
		private String color;
	}
	
}
