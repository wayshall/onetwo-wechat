package org.onetwo.ext.apiclient.qcloud.nlp.response;

import org.onetwo.ext.apiclient.qcloud.nlp.response.NlpV3Response.NlpV3BaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
public class NlpV3Response<T extends NlpV3BaseResponse> {

	@JsonProperty("Response")
	T response;
	
	@Data
	public static class NlpV3BaseResponse {
		@JsonProperty("RequestId")
		String requestId;
		
		@JsonProperty("Error")
		ErrorResponse error;
	}
	
	
	@Data
	public static class ErrorResponse {
		@JsonProperty("Code")
		String code;
		@JsonProperty("Message")
		String message;
	}

}
