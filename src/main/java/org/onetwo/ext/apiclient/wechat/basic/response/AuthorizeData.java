package org.onetwo.ext.apiclient.wechat.basic.response;

import lombok.Builder;
import lombok.Data;

import org.onetwo.common.utils.FieldName;
import org.onetwo.common.utils.ParamUtils;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2Keys;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.UrlConst;

/**
 * @author wayshall
 * <br/>
 */
@Data
@Builder
public class AuthorizeData {
	
	private String appid;
	@FieldName("redirect_uri")
	private String redirectUri;
	@FieldName("response_type")
	private String responseType = Oauth2Keys.RESPONSE_TYPE_CODE;
	private String scope = Oauth2Keys.SCOPE_SNSAPI_USERINFO;
	private String state;
	
	public String toAuthorizeUrl(){
		String url = UrlConst.OAUTH2_AUTHORIZE + "?" + ParamUtils.objectToParamString(this);
		return url;
	}

}
