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
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.onetwo.ext.apiclient.work.core.WorkConfigProvider;
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
	private WorkConfigProvider workConfigProvider;
	
	public WechatConfig getWechatConfig(WechatOAuth2Context contex) {
		WechatConfig wechatConfig = workConfigProvider.getWechatConfig(contex.getAppid());
		if (wechatConfig==null) {
			throw new WechatException("wechet config not found!").put("workConfigProvider", workConfigProvider)
																.put("appid", contex.getAppid());
		}
 		if (StringUtils.isNotBlank(contex.getAppid()) && !contex.getAppid().equals(wechatConfig.getAppid())) {
			throw new WechatException("config not found, error appid: " + contex.getAppid());
		}
		return wechatConfig;
	}
	
	@Override
	public WorkUserLoginInfo fetchOAuth2UserInfoFromServerWithCode(WechatOAuth2Context context) {
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
		AuthorizeData authorize = createAuthorize(wechatConfig.getAppid(), wechatConfig.getOauth2Scope(), redirectUrl, state);
		String authorizeUrl = authorize.toAuthorizeUrl();
		if (logger.isInfoEnabled()) {
			logger.info("[wechat oauth2] authorizeUrl url: {}", authorizeUrl);
		}
		return authorizeUrl;
	}


	public void setWorkConfigProvider(WorkConfigProvider workConfigProvider) {
		this.workConfigProvider = workConfigProvider;
	}
	
}

