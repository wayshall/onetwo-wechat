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
	
	public MpTemplateMessge(String templateId, String url, MiniprogramData miniprogram) {
		super();
		this.templateId = templateId;
		this.url = url;
		this.miniprogram = miniprogram;
	}
	

	public MpTemplateMessge addData(String key, MpTemplateMessgeData value){
		if(data==null){
			data = new LinkedHashMap<>();
		}
		data.put(key, value);
		return this;
	}
	
	public MpTemplateMessge addData(String key, String value){
		MpTemplateMessgeData d = new MpTemplateMessgeData();
		d.setValue(value);
		addData(key, d);
		return this;
	}

	@Data
	public static class MiniprogramData {
		private String appid;
		private String pagepath;
		@Builder
		public MiniprogramData(String appid, String pagepath) {
			super();
			this.appid = appid;
			this.pagepath = pagepath;
		}
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
