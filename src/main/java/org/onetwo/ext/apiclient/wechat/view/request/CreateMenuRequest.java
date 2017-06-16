package org.onetwo.ext.apiclient.wechat.view.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * @author wayshall
 * <br/>
 */
@Data
@ToString
@EqualsAndHashCode
public class CreateMenuRequest {
	
	private List<Button> button;
	
	@Data
	@ToString
	@EqualsAndHashCode
	public static class Button {
		private String type;
		private String name;
		private String key;
		private String url;
		@JsonProperty("media_id")
		private String mediaId;
		private String appid;
		private String pagepath;
		
		@JsonProperty("sub_button")
		private List<Button> subButton;
	}

}
