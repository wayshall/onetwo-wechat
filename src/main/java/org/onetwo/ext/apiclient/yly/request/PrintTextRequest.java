package org.onetwo.ext.apiclient.yly.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class PrintTextRequest extends YlyRequest {
	@JsonProperty("access_token")
	String accessToken;
	@JsonProperty("machine_code")
	String machineCode;
	
	/***
	 * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母 ，且在同一个client_id下唯一。详见商户订单号
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
