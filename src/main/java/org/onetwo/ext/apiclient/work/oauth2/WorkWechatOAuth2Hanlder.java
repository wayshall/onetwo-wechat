package org.onetwo.ext.apiclient.work.oauth2;

import javax.servlet.http.HttpServletRequest;

import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
import org.onetwo.ext.apiclient.wechat.oauth2.BaseOAuth2Hanlder;
import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;

/**
 * @author weishao zeng
 * <br/>
 */
public class WorkWechatOAuth2Hanlder extends BaseOAuth2Hanlder {

	@Override
	protected AuthorizeData getWechatAuthorizeData(HttpServletRequest request){
		RequestHoder holder = RequestHoder.builder().request(request).build();
		String redirectUrl = buildRedirectUrl(request);
		String state = getSessionStoreService().generateAndStoreOauth2State(holder);
		AuthorizeData authorize = createAuthorize(getWechatConfig(request), redirectUrl, state);
		return authorize;
	}
	
}

