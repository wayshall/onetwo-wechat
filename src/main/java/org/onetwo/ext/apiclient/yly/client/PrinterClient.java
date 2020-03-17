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
public interface PrinterClient {

	@PostMapping(value = "/printer/addprinter", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	YlyResponse addPrinter(AddPrinterRequest request);

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
	

}
