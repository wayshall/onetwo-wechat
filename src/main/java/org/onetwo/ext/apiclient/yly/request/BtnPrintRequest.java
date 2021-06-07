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
public class BtnPrintRequest extends YlyRequest {
	@JsonProperty("access_token")
	String accessToken;
	
	/***
	 * 自定义打印机名称(可填)
	 */
	@JsonProperty("response_type")
	String responseType;
	@JsonProperty("machine_code")
	String machineCode;

	@Builder
	public BtnPrintRequest(String clientId, int timestamp, String sign, String id, String accessToken,
			String responseType, String machineCode) {
		super(clientId, timestamp, sign, id);
		this.accessToken = accessToken;
		this.responseType = responseType;
		this.machineCode = machineCode;
	}
	
	public static enum ResponseTypes {
		btnopen,
		btnclose
	}

}
