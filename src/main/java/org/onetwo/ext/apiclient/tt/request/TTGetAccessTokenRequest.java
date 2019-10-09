package org.onetwo.ext.apiclient.tt.request;

import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.common.annotation.FieldName;
import org.onetwo.common.annotation.IgnoreField;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenType;
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
public class TTGetAccessTokenRequest {
	
	/**
	 * grant_type
	 */
	@NotBlank
	@FieldName("grant_type")
	private String grantType;
	@NotBlank
	private String appid;
	
	private String secret;
	@IgnoreField
	private AccessTokenType accessTokenType;
	
	@Builder
	public TTGetAccessTokenRequest(String grantType, String appid, String secret, AccessTokenType accessTokenType) {
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
