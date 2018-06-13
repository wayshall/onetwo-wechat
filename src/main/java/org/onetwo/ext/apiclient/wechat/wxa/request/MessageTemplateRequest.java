package org.onetwo.ext.apiclient.wechat.wxa.request;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageTemplateRequest implements Serializable {

	@NotBlank
	private String touser;

	@NotBlank
	@JsonProperty("template_id")
	private String templateId;

	private String page;
	
	@NotBlank
	@JsonProperty("form_id")
	private String formId;

	@JsonProperty("emphasis_keyword")
	private String emphasisKeyword;

	@NotNull
	private LinkedHashMap<String, TemplateMessageData> data;
	
	public MessageTemplateRequest addData(String key, TemplateMessageData value){
		if(data==null){
			data = new LinkedHashMap<>();
		}
		data.put(key, value);
		return this;
	}
	
	public MessageTemplateRequest addData(String key, String value){
		TemplateMessageData d = new TemplateMessageData();
		d.setValue(value);
		addData(key, d);
		return this;
	}

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	protected static class TemplateMessageData {
		private String value;
		private String color;
	}

}
