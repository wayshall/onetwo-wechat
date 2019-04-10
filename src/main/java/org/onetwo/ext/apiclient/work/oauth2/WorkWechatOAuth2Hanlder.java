package org.onetwo.ext.apiclient.work.oauth2;

import javax.servlet.http.HttpServletRequest;

import org.onetwo.common.exception.LoginException;
import org.onetwo.common.spring.copier.CopyUtils;
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
public class WorkWechatOAuth2Hanlder extends BaseOAuth2Hanlder<WorkUserLoginInfo> {

	@Autowired
	private WorkOauth2Client workOauth2Client;
	@Autowired
	private AccessTokenService accessTokenService;
	
	@Override
	protected WorkUserLoginInfo getOAuth2UserInfo(HttpServletRequest request, String code) {
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
		if (!res.isSuccess()) {
			throw new LoginException("企业微信授权失败了！");
		}
		WorkUserLoginInfo loginInfo = CopyUtils.copy(WorkUserLoginInfo.class, res);
		loginInfo.setAppid(wechatConfig.getAppid());
		return loginInfo;
	}

	@Override
	protected String getAuthorizeUrl(HttpServletRequest request){
		WechatConfig wechatConfig = getWechatConfig(request);
		RequestHoder holder = RequestHoder.builder().request(request).build();
		String redirectUrl = buildRedirectUrl(request);
		String state = getWechatOAuth2UserRepository().generateAndStoreOauth2State(holder, wechatConfig);
		AuthorizeData authorize = createAuthorize(wechatConfig, redirectUrl, state);
		String authorizeUrl = authorize.toAuthorizeUrl() + "#wechat_redirect";
		return authorizeUrl;
	}
	
}

