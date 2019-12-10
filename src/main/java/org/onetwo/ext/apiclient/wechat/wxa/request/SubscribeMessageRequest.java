package org.onetwo.ext.apiclient.wechat.wxa.request;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeMessageRequest implements Serializable {

	@NotBlank
	private String touser;

	@NotBlank
	@JsonProperty("template_id")
	private String templateId;

	private String page;
	
	@NotNull
	private LinkedHashMap<String, SubscribeMessageData> data;
	
	public SubscribeMessageRequest addData(String key, SubscribeMessageData value){
		if(data==null){
			data = new LinkedHashMap<>();
		}
		data.put(key, value);
		return this;
	}
	
	public SubscribeMessageRequest addData(String key, String value){
		SubscribeMessageData d = new SubscribeMessageData();
		d.setValue(value);
		addData(key, d);
		return this;
	}

	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	protected static class SubscribeMessageData {
		private String value;
	}

}
