package org.onetwo.ext.apiclient.wechat.serve.service;

import java.util.Optional;
import java.util.UUID;

import org.onetwo.boot.module.redis.TokenValidator;
import org.onetwo.ext.apiclient.wechat.serve.dto.WechatOAuth2Context;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2ClientKeys;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Setter;

/**
 * state参数仍然会依赖session
 * 
 * @author wayshall
 * <br/>
 */
public class HttpRequestStoreService<T extends OAuth2User> implements WechatOAuth2UserRepository<T> {
	@Setter
	private String userInfoKey = Oauth2ClientKeys.STORE_USER_INFO_KEY;
	@Autowired
	private TokenValidator tokenValidator;
	
	@SuppressWarnings("unchecked")
	@Override
	public Optional<T> getCurrentUser(WechatOAuth2Context request){
		T userInfo = (T)request.getRequest().getAttribute(userInfoKey);
		return Optional.ofNullable(userInfo);
	}
	@Override
	public void saveCurrentUser(WechatOAuth2Context request, T userInfo, boolean refresh){
		request.getRequest().setAttribute(userInfoKey, userInfo);
	}
	
	public boolean checkOauth2State(WechatOAuth2Context request) {
		String key = getStateKey(request.getState());
		boolean check = tokenValidator.check(key, request.getState(), false);
		return check;
	}

	protected String getStateKey(String state) {
		return "wechat_oauth2_state:" + state;
	}
	/****
	 * 生成state参数
	 * @author wayshall
	 * @param request
	 * @return
	 */
	public String generateAndStoreOauth2State(WechatOAuth2Context request){
		String state = UUID.randomUUID().toString();
		String key = getStateKey(state);
		this.tokenValidator.save(key, () -> state);
		return state;
	}
}
