package org.onetwo.ext.apiclient.wechat.serve.spi;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2ClientKeys;

/**
 * T OAuth2UserInfo
 * @author wayshall
 * <br/>
 */
public interface WechatOAuth2UserRepository<T extends OAuth2User> {

	Optional<T> getCurrentUser(RequestHoder request);

	void saveCurrentUser(RequestHoder request, T userInfo, boolean refresh);

	boolean checkOauth2State(RequestHoder request, WechatConfig wechatConfig, String state);

	/****
	 * 生成state参数
	 * @author wayshall
	 * @param request
	 * @return
	 */
	default String generateAndStoreOauth2State(RequestHoder request, WechatConfig wechatConfig){
		String state = UUID.randomUUID().toString();
		HttpSession session = request.getRequest().getSession();
		if(session!=null){
			session.setAttribute(Oauth2ClientKeys.STORE_STATE_KEY, state);
		}
		return state;
	}

	public interface OAuth2User {
		/***
		 * 是否过时
		 * @author weishao zeng
		 * @return
		 */
		default boolean isAccessTokenExpired() {
			return false;
		}
		/****
		 * accesstoken已过期时，是否采用刷新token的方式更新token和用户信息
		 * 
		 * @author weishao zeng
		 * @return
		 */
		default boolean isRefreshToken() {
			return false;
		}
	}
}