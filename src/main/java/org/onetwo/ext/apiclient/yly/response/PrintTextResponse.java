package org.onetwo.ext.apiclient.yly.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class PrintTextResponse extends YlyResponse {
	PrintTextBody body;
	
	@Data
	static public class PrintTextBody {
		String id;
		@JsonProperty("origin_id")
		String originId;
	}

}
