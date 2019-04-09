package org.onetwo.ext.apiclient.wechat.serve.service;

import java.util.Optional;

import org.onetwo.boot.module.redis.TokenValidator;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2ClientKeys;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Setter;

/**
 * @author wayshall
 * <br/>
 */
public class HttpRequestStoreService<T extends OAuth2User> implements WechatOAuth2UserRepository<T> {
	@Setter
	private String userInfoKey = Oauth2ClientKeys.STORE_USER_INFO_KEY;
	@Autowired
	private TokenValidator tokenValidator;
	
	protected String getUserKey(WechatConfig wechatConfig) {
		String key = userInfoKey + ":" + wechatConfig.getAppid();
		return key;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Optional<T> getCurrentUser(RequestHoder request){
		T userInfo = (T)request.getRequest().getAttribute(userInfoKey);
		return Optional.ofNullable(userInfo);
	}
	@Override
	public void saveCurrentUser(RequestHoder request, T userInfo, boolean refresh){
		request.getRequest().setAttribute(userInfoKey, userInfo);
	}
	
	@Override
	public boolean checkOauth2State(RequestHoder request, WechatConfig wechatConfig, String state){
		Boolean res = this.tokenValidator.checkOnlyOnce(getUserKey(wechatConfig), state, () ->  true);
		return res==null?false:res;
	}
	
	/****
	 * 生成state参数
	 * @author wayshall
	 * @param request
	 * @return
	 */
	@Override
	public String generateAndStoreOauth2State(RequestHoder request, WechatConfig wechatConfig){
		String state = this.tokenValidator.generateAlphanumeric(getUserKey(wechatConfig), 12);
		return state;
	}
}
