package org.onetwo.ext.apiclient.wechat.serve.spi;

import java.util.Optional;

import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;

/**
 * T OAuth2UserInfo
 * @author wayshall
 * <br/>
 */
public interface WechatOAuth2UserRepository<T extends OAuth2User> {

	Optional<T> getCurrentUser(RequestHoder request);

	void saveCurrentUser(RequestHoder request, T userInfo, boolean refresh);

	boolean checkOauth2State(RequestHoder request, String state);

	/****
	 * 生成state参数
	 * @author wayshall
	 * @param request
	 * @return
	 */
	String generateAndStoreOauth2State(RequestHoder request);

	public interface OAuth2User {
		/***
		 * 是否过时
		 * @author weishao zeng
		 * @return
		 */
		boolean isAccessTokenExpired();
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