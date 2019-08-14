package org.onetwo.ext.apiclient.qcloud.smscode.vo;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SmsCodeCheckRequest extends SmsCodeBaseRequest {

	@NotBlank(message="验证码不能为空")
	private String code;

	public SmsCodeCheckRequest() {
		super(null, null);
	}

	@Builder
	public SmsCodeCheckRequest(String mobile, String module, String code) {
		super(mobile, module);
		this.code = code;
	}
	
}

