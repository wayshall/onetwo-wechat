package org.onetwo.ext.apiclient.wechat.basic.api;

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
	
	@Override
	public AuthorizeData createAuthorize(String redirectUrl, String state){
		WechatConfig wechatConfig = Springs.getInstance().getBean(WechatConfig.class);
		String configRedirectUrl = wechatConfig.getOauth2RedirectUri();
		//check redirectUri?
		if(StringUtils.isBlank(configRedirectUrl)){
//			throw new ApiClientException(WechatClientError.OAUTH2_REDIRECT_URL_BLANK);
			configRedirectUrl = redirectUrl;
		}
		configRedirectUrl = LangUtils.encodeUrl(configRedirectUrl);
		return AuthorizeData.builder()
							.appid(wechatConfig.getAppid())
							.scope(wechatConfig.getOauth2Scope())
							.responseType(Oauth2Keys.RESPONSE_TYPE_CODE)
							.state(state)
							.redirectUri(configRedirectUrl)
							.build();
	}

}
