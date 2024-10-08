package org.onetwo.ext.apiclient.wechat.wxa.request;

import java.io.Serializable;
import java.util.LinkedHashMap;

import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotBlank;
import org.onetwo.common.utils.LangUtils;

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
		// 模板消息的字段一般不能超过20个字符,否则会抛奇怪的错误：argument invalid! data.thing1.value invalid
		d.setValue(LangUtils.ellipsis(value, 20));
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
