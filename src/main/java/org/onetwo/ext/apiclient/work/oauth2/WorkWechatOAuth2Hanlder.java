package org.onetwo.ext.apiclient.work.oauth2;

import javax.servlet.http.HttpServletRequest;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.oauth2.BaseOAuth2Hanlder;
import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;
import org.onetwo.ext.apiclient.work.oauth2.WorkOauth2Client.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class WorkWechatOAuth2Hanlder extends BaseOAuth2Hanlder<UserInfoResponse> {

	@Autowired
	private WorkOauth2Client workOauth2Client;
	@Autowired
	private AccessTokenService accessTokenService;
	
	@Override
	protected UserInfoResponse getOAuth2UserInfo(HttpServletRequest request, String code) {
		WechatConfig wechatConfig = this.getWechatConfig(request);
		GetAccessTokenRequest getRequest = GetAccessTokenRequest.builder()
																.appid(wechatConfig.getAppid())
																.secret(wechatConfig.getAppsecret())
																.accessTokenType(AccessTokenTypes.WORK_WECHAT)
																.build();
		AccessTokenInfo accessToken = this.accessTokenService.getOrRefreshAccessToken(getRequest);
		UserInfoResponse res = workOauth2Client.getUserInfo(accessToken, code);
		if (logger.isInfoEnabled()) {
			logger.info("get work wechat user success: {}", res);
		}
		return res;
	}

	@Override
	protected AuthorizeData getWechatAuthorizeData(HttpServletRequest request){
		WechatConfig wechatConfig = getWechatConfig(request);
		RequestHoder holder = RequestHoder.builder().request(request).build();
		String redirectUrl = buildRedirectUrl(request);
		String state = getWechatOAuth2UserRepository().generateAndStoreOauth2State(holder, wechatConfig);
		AuthorizeData authorize = createAuthorize(wechatConfig, redirectUrl, state);
		return authorize;
	}
	
}

