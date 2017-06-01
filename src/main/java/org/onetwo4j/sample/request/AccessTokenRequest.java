package org.onetwo4j.sample.request;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode
@Builder
public class AccessTokenRequest {
	
	/**
	 * grant_type
	 */
	@NotBlank
	private String grantType;
	@NotBlank
	private String appid;
	@NotBlank
	private String secret;
	
}
