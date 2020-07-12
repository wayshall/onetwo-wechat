package org.onetwo.ext.apiclient.wechat.oauth2.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.ext.apiclient.wechat.oauth2.OAuth2UserInfo;
import org.onetwo.ext.apiclient.wechat.oauth2.WechatOAuth2Hanlder;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2Request;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author weishao zeng
 * <br/>
 */
abstract public class WechatLoginBaseController<LoginData> {
	protected final Logger logger = JFishLoggerFactory.getLogger(getClass());

//	@Autowired
//	private WorkQRConnectHandler workQRConnectHandler;
	@Autowired
	private WechatOAuth2Hanlder wechatOAuth2Hanlder;
	
	@RequestMapping(value="/oauth2",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LoginData oauth2(OAuth2Request oauth2Request, HttpServletRequest request, HttpServletResponse response){
		OAuth2UserInfo userLoginInfo = wechatOAuth2Hanlder.handleInController(oauth2Request, request, response);
//		logger.info("userInfoResponse: {}", workUserLoginInfo);
		if (userLoginInfo==null) {
			return null;
		} else {
			LoginData token = loginByWechatUser(userLoginInfo);
			return token;
		}
	}
	
//	@RequestMapping(value="/qrConnect",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public LoginData qrConnect(OAuth2Request oauth2Request, HttpServletRequest request, HttpServletResponse response){
//		WorkUserLoginInfo workUserLoginInfo = workQRConnectHandler.handleInController(oauth2Request, request, response);
////		logger.info("userInfoResponse: {}", workUserLoginInfo);
//		if (workUserLoginInfo==null) {
//			return null;
//		} else {
//			LoginData token = loginByWorkWechatUser(workUserLoginInfo);
//			return token;
//		}
//	}
	
	/****
	 * 实现具体的业务登录逻辑
	 * @author weishao zeng
	 * @param workUserLoginInfo
	 * @return
	 */
	abstract protected LoginData loginByWechatUser(OAuth2UserInfo userLoginInfo);

}
