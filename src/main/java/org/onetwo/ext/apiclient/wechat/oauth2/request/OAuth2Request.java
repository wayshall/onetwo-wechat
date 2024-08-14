package org.onetwo.ext.apiclient.wechat.oauth2.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
public class OAuth2Request {
	@NotBlank(message = "appid不能为空！")
	private String appid;
	private String code;
	private String state;
	private String redirectUrl;

}

