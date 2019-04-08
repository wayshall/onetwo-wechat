package org.onetwo.ext.apiclient.wechat.accesstoken.request;

import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.common.annotation.IgnoreField;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wayshall
 * <br/>
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class AppidRequest {
	
	@NotBlank
	private String appid;
	@IgnoreField
	private AccessTokenTypes accessTokenType;
	
	@Builder
	public AppidRequest(String appid, AccessTokenTypes accessTokenType) {
		super();
		if (accessTokenType==null) {
			this.accessTokenType = AccessTokenTypes.WECHAT;
		} else {
			this.accessTokenType = accessTokenType;
		}
		this.appid = appid;
	}
	
}