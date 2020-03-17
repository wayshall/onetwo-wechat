package org.onetwo.ext.apiclient.yly.client;

import org.onetwo.ext.apiclient.yly.core.YlyApiClient;
import org.onetwo.ext.apiclient.yly.core.YlyRequest;
import org.onetwo.ext.apiclient.yly.core.YlyResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@YlyApiClient
public interface PrintClient {

	@PostMapping(value = "/print/index", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	PrintTextResponse printText(PrintTextRequest request);

	@Data
	@EqualsAndHashCode(callSuper = false)
	public class PrintTextRequest extends YlyRequest {
		@JsonProperty("access_token")
		String accessToken;
		@JsonProperty("machine_code")
		String machineCode;
		
		/***
		 * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母 ，且在同一个client_id下唯一
		 */
		@JsonProperty("origin_id")
		String originId;
		
		String content;

		@Builder
		public PrintTextRequest(String clientId, int timestamp, String sign, String id, String accessToken,
				String machineCode, String originId, String content) {
			super(clientId, timestamp, sign, id);
			this.accessToken = accessToken;
			this.machineCode = machineCode;
			this.originId = originId;
			this.content = content;
		}

	}
	

	@Data
	@EqualsAndHashCode(callSuper = false)
	public class PrintTextResponse extends YlyResponse {
		PrintTextBody body;
	}

	@Data
	public class PrintTextBody{
		String id;
		@JsonProperty("origin_id")
		String originId;
	}
}
