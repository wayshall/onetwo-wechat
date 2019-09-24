package org.onetwo.ext.apiclient.wechat.oauth2.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.common.annotation.FieldName;
import org.onetwo.ext.apiclient.wechat.basic.request.AuthBaseRequest;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.GrantTypeKeys;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
public class OAuth2AccessTokenRequest extends AuthBaseRequest {
	
	/*@Value("#{wechatConfig.appid}")
	@NotBlank
	private String appid;
	@Value("#{wechatConfig.appsecret}")
	@NotBlank
	private String secret;*/
	/***
	 * 填写第一步获取的code参数
	 */
	@NotBlank
	private String code;
	@NotBlank
	@FieldName("grant_type")
	private String grantType;

	@Builder
	public OAuth2AccessTokenRequest(String appid, String secret, String code, String grantType) {
		super(appid, secret);
		this.code = code;
		if(StringUtils.isBlank(grantType)){
			this.grantType = GrantTypeKeys.AUTHORIZATION_CODE;
		}else{
			this.grantType = grantType;
		}
	}
	
	

}
