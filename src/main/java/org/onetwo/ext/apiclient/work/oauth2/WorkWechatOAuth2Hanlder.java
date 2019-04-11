package org.onetwo.ext.apiclient.work.oauth2;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.common.spring.copier.CopyUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.basic.response.AuthorizeData;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.oauth2.BaseOAuth2Hanlder;
import org.onetwo.ext.apiclient.wechat.serve.dto.WechatOAuth2Context;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;
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
	protected WorkUserLoginInfo getOAuth2UserInfo(WechatOAuth2Context context) {
		WechatConfig wechatConfig = context.getWechatConfig();
		GetAccessTokenRequest getRequest = GetAccessTokenRequest.builder()
																.appid(wechatConfig.getAppid())
																.secret(wechatConfig.getAppsecret())
																.accessTokenType(AccessTokenTypes.WORK_WECHAT)
																.build();
		AccessTokenInfo accessToken = this.accessTokenService.getOrRefreshAccessToken(getRequest);
		UserInfoResponse res = workOauth2Client.getUserInfo(accessToken, context.getCode());
		if (logger.isInfoEnabled()) {
			logger.info("get work wechat user success: {}", res);
		}
		if (StringUtils.isBlank(res.getUserId())) {
			throw new ApiClientException(WechatClientErrors.OAUTH2_USER_NOT_WORK_MEMBER);
		}
		WorkUserLoginInfo loginInfo = CopyUtils.copy(WorkUserLoginInfo.class, res);
		loginInfo.setAppid(wechatConfig.getAppid());
		return loginInfo;
	}

	@Override
	protected String getAuthorizeUrl(WechatOAuth2Context context){
		WechatConfig wechatConfig = context.getWechatConfig();
		String redirectUrl = buildRedirectUrl(context);
		String state = getWechatOAuth2UserRepository().generateAndStoreOauth2State(context);
		AuthorizeData authorize = createAuthorize(wechatConfig, redirectUrl, state);
		String authorizeUrl = authorize.toAuthorizeUrl() + "#wechat_redirect";
		return authorizeUrl;
	}
	
}

