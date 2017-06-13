package org.onetwo.ext.apiclient.wechat.core;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author wayshall
 * <br/>
 */
//@ConfigurationProperties("webchat")
@Data
public class DefaultWechatConfig implements WechatConfig{
	private String token;
	
	@Value("${webchat.grantType:client_credential}")
	private String grantType;
	
	@Value("${webchat.appid}")
	private String appid;
	
	@Value("${webchat.appsecret}")
	private String appsecret;
}
