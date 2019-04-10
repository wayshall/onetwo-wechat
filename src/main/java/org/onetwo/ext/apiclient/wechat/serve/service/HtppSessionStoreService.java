package org.onetwo.ext.apiclient.wechat.serve.service;

import java.util.Optional;

import org.onetwo.common.web.utils.RequestUtils;
import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2ClientKeys;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wayshall
 * <br/>
 */
@Slf4j
public class HtppSessionStoreService<T extends OAuth2User> implements WechatOAuth2UserRepository<T> {

	private String userInfoKey = Oauth2ClientKeys.STORE_USER_INFO_KEY;

	@SuppressWarnings("unchecked")
	@Override
	public Optional<T> getCurrentUser(RequestHoder request){
		return RequestUtils.getSession(request.getRequest()).map(session->{
			log.info("getCurrentUser session id: {}", session.getId());
			T userInfo = (T)session.getAttribute(userInfoKey);
			return userInfo;
		});
	}
	@Override
	public void saveCurrentUser(RequestHoder request, T userInfo, boolean refresh){
		RequestUtils.getSession(request.getRequest()).ifPresent(session->{
			log.info("saveCurrentUser session id: {}", session.getId());
			session.setAttribute(userInfoKey, userInfo);
		});
	}
	
}
