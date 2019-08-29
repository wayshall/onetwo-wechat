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
	/***
	 * 企业微信时使用
	 */
	private String agentId;
	/****
	 * secret可不写，根据appid和agentid到配置里读取
	 */
//	@NotBlank
	private String secret;
	@IgnoreField
	private AccessTokenTypes accessTokenType;
	
	@Builder
	public GetAccessTokenRequest(String grantType, String appid, String secret, AccessTokenTypes accessTokenType, String agentId) {
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
		this.agentId = agentId;
	}
	
	@Deprecated
	public String getSecret() {
		return secret;
	}
	
}
