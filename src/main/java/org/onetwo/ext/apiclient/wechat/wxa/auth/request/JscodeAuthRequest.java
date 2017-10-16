package org.onetwo.ext.apiclient.wechat.wxa.auth.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import org.onetwo.common.utils.FieldName;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.basic.request.AuthBaseRequest;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.GrantTypeKeys;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper=false)
@Data
public class JscodeAuthRequest extends AuthBaseRequest {
	
	@NotNull
	@FieldName("js_code")
	String jsCode;
	@NotNull
	@FieldName("grant_type")
	String grantType;
	
	@Builder
	public JscodeAuthRequest(String appid, String secret, String jsCode, String grantType) {
		super(appid, secret);
		this.jsCode = jsCode;
		if(StringUtils.isBlank(grantType)){
			this.grantType = GrantTypeKeys.AUTHORIZATION_CODE;
		}else{
			this.grantType = grantType;
		}
	}

}
