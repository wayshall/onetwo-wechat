package org.onetwo.ext.apiclient.wechat.basic.request;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode
public class ServerAuthRequest {
	@NotBlank
	private String signature;
	@NotBlank
	private String timestamp;
	@NotBlank
	private String nonce;
	@NotBlank
	private String echostr;
}
