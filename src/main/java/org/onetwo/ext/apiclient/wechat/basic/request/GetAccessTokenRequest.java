package org.onetwo.ext.apiclient.wechat.basic.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.common.utils.FieldName;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.GrantTypeKeys;

/**
 * @author wayshall
 * <br/>
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
//@Builder
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
	
	@Builder
	public GetAccessTokenRequest(String grantType, String appid, String secret) {
		super();
		if(StringUtils.isBlank(grantType)){
			this.grantType = GrantTypeKeys.CLIENT_CREDENTIAL;
		}else{
			this.grantType = grantType;
		}
		this.appid = appid;
		this.secret = secret;
	}
	
}
