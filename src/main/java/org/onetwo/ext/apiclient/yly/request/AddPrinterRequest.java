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
public class AddPrinterRequest extends YlyRequest {
	@JsonProperty("access_token")
	String accessToken;
	/****
	 * 易联云终端密钥
	 */
	String msign;
	
	/***
	 * 自定义打印机名称(可填)
	 */
	@JsonProperty("print_name")
	String printName;
	@JsonProperty("machine_code")
	String machineCode;
	/***
	 * 手机卡号码(可填)
	 */
	String phone;

	@Builder
	public AddPrinterRequest(String clientId, int timestamp, String sign, String id, String accessToken,
			String msign, String printName, String phone, String machineCode) {
		super(clientId, timestamp, sign, id);
		this.accessToken = accessToken;
		this.msign = msign;
		this.printName = printName;
		this.phone = phone;
		this.machineCode = machineCode;
	}

}
