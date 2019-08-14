package org.onetwo.ext.apiclient.wechat.accesstoken.request;

import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.common.annotation.FieldName;
import org.onetwo.common.annotation.IgnoreField;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.GrantTypeKeys;

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
	@IgnoreField
	private AccessTokenTypes accessTokenType;
	
	@Builder
	public GetAccessTokenRequest(String grantType, String appid, String secret, AccessTokenTypes accessTokenType) {
		super();
		if (StringUtils.isBlank(grantType)) {
			this.grantType = GrantTypeKeys.CLIENT_CREDENTIAL;
		} else {
			this.grantType = grantType;
		}
		if (accessTokenType==null) {
			this.accessTokenType = AccessTokenTypes.WECHAT;
		} else {
			this.accessTokenType = accessTokenType;
		}
		this.appid = appid;
		this.secret = secret;
	}
	
}
