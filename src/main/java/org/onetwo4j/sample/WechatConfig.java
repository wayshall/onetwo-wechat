package org.onetwo4j.sample;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wayshall
 * <br/>
 */
@ConfigurationProperties("webchat")
@Data
public class WechatConfig {
	private String token;
	private String grantType = "client_credential";
	private String appid;
	private String appsecret;
}
