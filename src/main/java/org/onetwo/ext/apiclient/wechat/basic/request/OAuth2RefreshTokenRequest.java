package org.onetwo.ext.apiclient.wechat.basic.request;

import lombok.Builder;
import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.common.utils.FieldName;
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
