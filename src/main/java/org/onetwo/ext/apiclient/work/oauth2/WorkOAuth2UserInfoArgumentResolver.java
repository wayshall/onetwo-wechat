package org.onetwo.ext.apiclient.work.oauth2;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onetwo.common.spring.mvc.annotation.BootMvcArgumentResolver;
import org.onetwo.ext.apiclient.wechat.oauth2.OAuth2UserInfo;
import org.onetwo.ext.apiclient.wechat.serve.dto.WechatOAuth2Context.RequestWechatOAuth2Context;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatOAuth2UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author wayshall
 * <br/>
 */
@BootMvcArgumentResolver
public class WorkOAuth2UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private WorkWechatOAuth2Hanlder wechatOAuth2Hanlder;
	@Autowired
	private WechatOAuth2UserRepository<OAuth2UserInfo> sessionStoreService;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return WorkOAuth2UserInfo.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		
		RequestWechatOAuth2Context context = new RequestWechatOAuth2Context(request);
		context.setWechatConfig(wechatOAuth2Hanlder.getWechatConfig(context));
		
		Optional<OAuth2UserInfo> userOpt = sessionStoreService.getCurrentUser(context);
		if(!userOpt.isPresent()){
			wechatOAuth2Hanlder.preHandle(request, webRequest.getNativeResponse(HttpServletResponse.class), null);
			userOpt = sessionStoreService.getCurrentUser(context);
		}
		return userOpt.orElse(null);
	}
	
}
