package org.onetwo.ext.apiclient.wechat.oauth2.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import org.onetwo.common.annotation.FieldName;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.GrantTypeKeys;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class OAuth2RefreshTokenRequest {

	@NotBlank
	private String appid;
	/***
	 * 填写为refresh_token
	 */
	@FieldName("grant_type")
	@NotBlank
	private String grant_type = GrantTypeKeys.REFRESH_TOKEN;
	
	@FieldName("refresh_token")
	@NotBlank
	private String refreshToken;
	
	@Builder
	public OAuth2RefreshTokenRequest(String appid, String refreshToken) {
		super();
		this.appid = appid;
		this.refreshToken = refreshToken;
	}

}
