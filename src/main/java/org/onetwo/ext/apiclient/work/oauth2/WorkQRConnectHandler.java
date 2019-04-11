package org.onetwo.ext.apiclient.work.oauth2;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.expr.ExpressionFacotry;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.common.web.utils.RequestUtils;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.serve.dto.WechatOAuth2Context;

/**
 * @author weishao zeng
 * <br/>
 */
public class WorkQRConnectHandler extends WorkWechatOAuth2Hanlder {
	
	private static final String QR_CONNECT_URL_TEMPLATE = "https://open.work.weixin.qq.com/wwopen/sso/qrConnect?appid=${appid}&agentid=${agentid}&redirect_uri=${redirectUri}&state=${state}";


	protected String buildRedirectUrl(HttpServletRequest request, WechatConfig wechatConfig){
		String redirectUrl = wechatConfig.getQrConnectRedirectUri();
		//check redirectUri?
		if(StringUtils.isBlank(redirectUrl)){
			redirectUrl = RequestUtils.buildFullRequestUrl(request.getScheme(), request.getServerName(), 80, request.getRequestURI(), request.getQueryString());
		}
		redirectUrl = LangUtils.encodeUrl(redirectUrl);
		return redirectUrl;
	}

	@Override
	protected String getAuthorizeUrl(WechatOAuth2Context context){
		WechatConfig wechatConfig = context.getWechatConfig();
		String redirectUrl = buildRedirectUrl(context);
		String state = getWechatOAuth2UserRepository().generateAndStoreOauth2State(context);
		String authorizeUrl = ExpressionFacotry.DOLOR.parse(
													QR_CONNECT_URL_TEMPLATE, 
													"appid", wechatConfig.getAppid(), 
													"agentid", wechatConfig.getAgentId(), 
													"redirectUri", redirectUrl, 
													"state", state
												);
		return authorizeUrl;
	}
	
}

