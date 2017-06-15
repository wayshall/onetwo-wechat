package org.onetwo.ext.apiclient.wechat.basic.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.common.utils.FieldName;

/**
 * @author wayshall
 * <br/>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAccessTokenRequest {
	
	/**
	 * grant_type
	 */
	@NotBlank
	@FieldName("grant_type")
	private String grantType;
	@NotBlank
	private String appid;
	@NotBlank
	private String secret;
	
}
