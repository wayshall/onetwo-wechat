package org.onetwo.ext.apiclient.qcloud.sms.vo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
public class SendSmsRequest {
	String nationCode;
	String phoneNumber;
	int templId;
	List<String> params;
	String sign;
	String extend;
	String ext;
	
	@Builder
	public SendSmsRequest(String nationCode, String phoneNumber, int templId, String sign,
			String extend, String ext, List<String> params) {
		super();
		if (StringUtils.isBlank(nationCode)) {
			this.nationCode = "86";
		} else {
			this.nationCode = nationCode;
		}
		this.phoneNumber = phoneNumber;
		this.templId = templId;
		this.params = params;
		this.sign = sign;
		this.extend = extend;
		this.ext = ext;
	}
	
}

