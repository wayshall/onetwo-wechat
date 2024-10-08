package org.onetwo.ext.apiclient.tt.request;

import javax.validation.constraints.NotBlank;
import org.onetwo.common.annotation.FieldName;
import org.onetwo.common.annotation.IgnoreField;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.tt.core.TTAccessTokenProvider.TTAccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenType;
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
	private AccessTokenType accessTokenType = TTAccessTokenTypes.TTAPP;
	
	@Builder
	public TTGetAccessTokenRequest(String grantType, String appid, String secret) {
		if (StringUtils.isBlank(grantType)) {
			this.grantType = GrantTypeKeys.CLIENT_CREDENTIAL;
		} else {
			this.grantType = grantType;
		}
		this.appid = appid;
		this.secret = secret;
	}
	
}
