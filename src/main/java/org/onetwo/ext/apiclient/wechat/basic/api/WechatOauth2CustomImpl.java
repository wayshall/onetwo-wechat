package org.onetwo.ext.apiclient.wechat.basic.api;

import java.util.UUID;

import org.onetwo.common.spring.Springs;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2Keys;
import org.springframework.stereotype.Service;

/**
 * @author wayshall
 * <br/>
 */
@Service
public class WechatOauth2CustomImpl implements WechatOauth2Custom {
	
	public AuthorizeData createAuthorize(){
		WechatConfig wechatConfig = Springs.getInstance().getBean(WechatConfig.class);
		String redirectUri = wechatConfig.getOauth2RedirectUri();
		//check redirectUri?
		if(StringUtils.isNotBlank(redirectUri)){
			redirectUri = LangUtils.encodeUrl(redirectUri);
		}
		return AuthorizeData.builder()
							.appid(wechatConfig.getAppid())
							.scope(Oauth2Keys.SCOPE_SNSAPI_USERINFO)
							.responseType(Oauth2Keys.RESPONSE_TYPE_CODE)
							.state(UUID.randomUUID().toString())
							.redirectUri(redirectUri)
							.build();
	}

}
