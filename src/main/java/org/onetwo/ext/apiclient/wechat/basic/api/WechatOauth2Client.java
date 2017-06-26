package org.onetwo.ext.apiclient.wechat.basic.api;

import org.onetwo.common.apiclient.annotation.InjectProperties;
import org.onetwo.ext.apiclient.wechat.basic.request.OAuth2AccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.request.OAuth2RefreshTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.request.OAuth2UserInfoRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.OAuth2AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.basic.response.OAuth2RefreshTokenResponse;
import org.onetwo.ext.apiclient.wechat.basic.response.OAuth2UserInfoResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.UrlConst;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842">view api doc</a>
 * 
 * @author wayshall
 * <br/>
 */
@WechatApiClient(url=UrlConst.API_DOMAIN_URL)
public interface WechatOauth2Client extends WechatOauth2Custom {
	
	/****
	 * 第二步：通过code换取网页授权access_token
	 * 
	 * @author wayshall
	 * @param request
	 * @return
	 */
	@GetMapping(value="/sns/oauth2/access_token", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	OAuth2AccessTokenResponse getAccessToken(@InjectProperties OAuth2AccessTokenRequest request);

	/***
	 * 第三步：刷新access_token（如果需要）
		由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权。
	 * @author wayshall
	 * @param request
	 * @return
	 */
	OAuth2RefreshTokenResponse refreshToken(OAuth2RefreshTokenRequest request);
	
	
	/***
	 * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
	 * @author wayshall
	 * @param openid
	 * @return
	 */
	@GetMapping(value="/sns/userinfo?lang=zh_CN", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	OAuth2UserInfoResponse getUserInfo(OAuth2UserInfoRequest request);
	

}
