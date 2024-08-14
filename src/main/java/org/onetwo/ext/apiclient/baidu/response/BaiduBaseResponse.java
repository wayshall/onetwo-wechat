package org.onetwo.ext.apiclient.baidu.response;

import org.onetwo.common.utils.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BaiduBaseResponse {
	/***
	 * 唯一的log id，用于问题定位
	 */
	@JsonProperty("log_id")
	String logId;
	
	@JsonProperty("error_code")
	String errorCode;
	
	@JsonProperty("error_msg")
	String errorMsg;
	
	public boolean isSuccess() {
		return StringUtils.isBlank(errorCode);
	}

}
