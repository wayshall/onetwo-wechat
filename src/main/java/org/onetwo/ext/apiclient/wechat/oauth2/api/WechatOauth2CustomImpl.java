package org.onetwo.ext.apiclient.wechat.oauth2.api;

import org.onetwo.common.spring.Springs;
import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.oauth2.WechatOAuth2Hanlder;
import org.springframework.stereotype.Service;

/**
 * @author wayshall
 * <br/>
 */
@Service
public class WechatOauth2CustomImpl implements WechatOauth2Custom {
	
	@Override
	public AuthorizeData createAuthorize(String redirectUrl, String state){
		WechatConfig wechatConfig = Springs.getInstance().getBean(WechatConfig.class, true);
		return WechatOAuth2Hanlder.createAuthorize(wechatConfig, redirectUrl, state);
	}

}
