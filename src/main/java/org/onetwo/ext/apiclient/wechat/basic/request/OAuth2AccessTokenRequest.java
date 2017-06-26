package org.onetwo.ext.apiclient.wechat.basic.request;

import lombok.Builder;
import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.common.utils.FieldName;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.GrantTypeKeys;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class OAuth2AccessTokenRequest {
	@Value("#{wechatConfig.appid}")
	@NotBlank
	private String appid;
	@Value("#{wechatConfig.appsecret}")
	@NotBlank
	private String secret;
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
		super();
		this.appid = appid;
		this.secret = secret;
		this.code = code;
		if(StringUtils.isBlank(grantType)){
			this.grantType = GrantTypeKeys.AUTHORIZATION_CODE;
		}else{
			this.grantType = grantType;
		}
	}
	
	

}
