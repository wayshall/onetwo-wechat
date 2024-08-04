package org.onetwo.ext.apiclient.wechat.serve.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode
public class BaseServeParam {
	@NotBlank
	private String timestamp;
	@NotBlank
	private String nonce;
	
	private String clientId;
}
