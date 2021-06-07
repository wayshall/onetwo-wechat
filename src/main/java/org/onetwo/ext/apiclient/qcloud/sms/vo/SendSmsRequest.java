package org.onetwo.ext.apiclient.qcloud.sms.vo;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@NoArgsConstructor
public class SendSmsRequest {
	String nationCode;
	String phoneNumber;
	String[] phoneNumbers;
	int templId;
	List<String> params;
	/***
	 * 签名，以[sign]的方式放在短信前面
	 */
	String sign;
	String extend;
	String ext;
	
	@Builder
	public SendSmsRequest(String nationCode, String phoneNumber, int templId, String sign,
			String extend, String ext, List<String> params, String[] phoneNumbers) {
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
		this.phoneNumbers = phoneNumbers;
	}
	
}

