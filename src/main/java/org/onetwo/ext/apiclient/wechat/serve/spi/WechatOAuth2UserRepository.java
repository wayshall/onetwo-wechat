package org.onetwo.ext.apiclient.wechat.serve.spi;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.ext.apiclient.wechat.serve.dto.WechatOAuth2Context;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2ClientKeys;
import org.slf4j.Logger;

/**
 * T OAuth2UserInfo
 * @author wayshall
 * <br/>
 */
public interface WechatOAuth2UserRepository<T extends OAuth2User> {

	Optional<T> getCurrentUser(WechatOAuth2Context request);

	void saveCurrentUser(WechatOAuth2Context request, T userInfo, boolean refresh);

	default boolean checkOauth2State(WechatOAuth2Context request) {
		HttpSession session = request.getRequest().getSession();
		if(session!=null){
			String state = request.getState();
			String storedState = (String)session.getAttribute(Oauth2ClientKeys.STORE_STATE_KEY);
			Logger logger = JFishLoggerFactory.getCommonLogger();
			logger.info("storedState: {}, param state: {}", storedState, state);
			session.removeAttribute(Oauth2ClientKeys.STORE_STATE_KEY);
			return StringUtils.isNotBlank(state) && state.equals(storedState);
		}
		return false;
	}

	/****
	 * 生成state参数
	 * @author wayshall
	 * @param request
	 * @return
	 */
	default String generateAndStoreOauth2State(WechatOAuth2Context request){
		String state = UUID.randomUUID().toString();
		HttpSession session = request.getRequest().getSession();
		if(session!=null){
			Logger logger = JFishLoggerFactory.getCommonLogger();
			logger.info("storedState: {}", state);
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