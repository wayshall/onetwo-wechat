package org.onetwo.ext.apiclient.wechat.basic.request;

import lombok.Builder;
import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;
import org.onetwo.common.utils.FieldName;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;

/**
 * @author wayshall
 * <br/>
 */
@Data
@Builder
public class Oauth2AccessTokenRequest {
	
	@NotBlank
	private String appid;
	@NotBlank
	private String secret;
	/***
	 * 填写第一步获取的code参数
	 */
	@NotBlank
	private String code;
	@NotBlank
	@FieldName("grant_type")
	private String grantType = WechatConstants.GT_AUTHORIZATION_CODE;

}
