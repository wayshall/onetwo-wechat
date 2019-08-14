package org.onetwo.ext.apiclient.qcloud.smscode.vo;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SmsCodeRequest extends SmsCodeBaseRequest {

	@NotNull(message="短信模板不能为空")
	private Integer templateId;
	
	private int codeLength = 4;
	private int validInMinutes = 2;
	private String sign;
	

	public SmsCodeRequest() {
		super(null, null);
	}
	
	@Builder
	public SmsCodeRequest(String mobile, String module, Integer templateId, Integer codeLength, Integer validInMinutes,
			String sign) {
		super(mobile, module);
		this.templateId = templateId;
		if (codeLength!=null) {
			this.codeLength = codeLength;
		}
		if (validInMinutes!=null) {
			this.validInMinutes = validInMinutes;
		}
		this.sign = sign;
	}
	
	
}

