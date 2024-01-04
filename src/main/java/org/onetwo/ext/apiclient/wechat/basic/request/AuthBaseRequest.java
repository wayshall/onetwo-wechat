package org.onetwo.ext.apiclient.wechat.basic.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class AuthBaseRequest implements Serializable {
	
	/**
	 * @author wayshall
	 * 
	 */
	private static final long serialVersionUID = 7208934747952443024L;

	@NotNull
	@NotBlank
//	@Value("#{wechatConfig.appid}")
	private String appid;
	@NotNull
	@NotBlank
//	@Value("#{wechatConfig.appsecret}")
	private String secret;
	
	public AuthBaseRequest(String appid, String secret) {
		super();
		this.appid = appid;
		this.secret = secret;
	}
	
}
