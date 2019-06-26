package org.onetwo.ext.apiclient.wechat.oauth2.request;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
public class OAuth2Request {
	
	private String appid;
	private String code;
	private String state;

}

