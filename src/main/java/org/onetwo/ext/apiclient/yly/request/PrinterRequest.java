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
public class PrinterRequest extends YlyRequest {
	@JsonProperty("access_token")
	String accessToken;
	
	@JsonProperty("machine_code")
	String machineCode;

	@Builder
	public PrinterRequest(String clientId, int timestamp, String sign, String id, String accessToken,
			String machineCode) {
		super(clientId, timestamp, sign, id);
		this.accessToken = accessToken;
		this.machineCode = machineCode;
	}
	
}
