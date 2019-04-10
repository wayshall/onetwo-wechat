package org.onetwo.ext.apiclient.wechat.serve.service;

import java.util.Optional;

import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository.OAuth2User;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2ClientKeys;

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
	/*@Autowired
	private TokenValidator tokenValidator;*/
	
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
}
