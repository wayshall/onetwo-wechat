package org.onetwo.ext.apiclient.wechat.basic.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

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
	@Value("#{wechatConfig.appid}")
	@NotBlank
	private String appid;
	@NotNull
	@Value("#{wechatConfig.appsecret}")
	@NotBlank
	private String secret;
	
	public AuthBaseRequest(String appid, String secret) {
		super();
		this.appid = appid;
		this.secret = secret;
	}
	
}
