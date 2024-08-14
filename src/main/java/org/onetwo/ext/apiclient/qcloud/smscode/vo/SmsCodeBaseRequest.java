package org.onetwo.ext.apiclient.qcloud.smscode.vo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
public class SmsCodeBaseRequest {

	@NotBlank(message="手机不能为空！")
	private String mobile;
	@NotBlank(message="模块标识不能为空")
	private String module;
	
	public SmsCodeBaseRequest(String mobile, String module) {
		super();
		this.mobile = mobile;
		this.module = module;
	}
	
}

