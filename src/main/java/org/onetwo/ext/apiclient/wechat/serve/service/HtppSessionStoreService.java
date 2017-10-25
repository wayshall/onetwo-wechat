package org.onetwo.ext.apiclient.wechat.serve.service;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.web.utils.RequestUtils;
import org.onetwo.ext.apiclient.wechat.oauth2.OAuth2UserInfo;
import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatSessionRepository;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2ClientKeys;
import org.springframework.stereotype.Service;

/**
 * @author wayshall
 * <br/>
 */
@Service
@Slf4j
public class HtppSessionStoreService implements WechatSessionRepository {

	public static final String USER_INFO_KEY = Oauth2ClientKeys.STORE_USER_INFO_KEY;
	
	@Override
	public Optional<OAuth2UserInfo> getCurrentUser(RequestHoder request){
		return RequestUtils.getSession(request.getRequest()).map(session->{
			log.info("getCurrentUser session id: {}", session.getId());
			OAuth2UserInfo userInfo = (OAuth2UserInfo)session.getAttribute(USER_INFO_KEY);
			return userInfo;
		});
	}
	@Override
	public void saveCurrentUser(RequestHoder request, OAuth2UserInfo userInfo, boolean refresh){
		RequestUtils.getSession(request.getRequest()).ifPresent(session->{
			log.info("saveCurrentUser session id: {}", session.getId());
			session.setAttribute(USER_INFO_KEY, userInfo);
		});
	}
	
	@Override
	public boolean checkOauth2State(RequestHoder request, String state){
		HttpSession session = request.getRequest().getSession();
		if(session!=null){
			String storedState = (String)session.getAttribute(Oauth2ClientKeys.STORE_STATE_KEY);
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
	@Override
	public String generateAndStoreOauth2State(RequestHoder request){
		String state = UUID.randomUUID().toString();
		HttpSession session = request.getRequest().getSession();
		if(session!=null){
			session.setAttribute(Oauth2ClientKeys.STORE_STATE_KEY, state);
		}
		return state;
	}
}
