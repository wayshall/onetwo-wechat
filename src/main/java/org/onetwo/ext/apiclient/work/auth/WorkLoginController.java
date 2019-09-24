package org.onetwo.ext.apiclient.work.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.ext.apiclient.wechat.oauth2.request.OAuth2Request;
import org.onetwo.ext.apiclient.work.oauth2.WorkQRConnectHandler;
import org.onetwo.ext.apiclient.work.oauth2.WorkUserLoginInfo;
import org.onetwo.ext.apiclient.work.oauth2.WorkWechatOAuth2Hanlder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wayshall
 * <br/>
 */
//@RequestMapping("/auth/login")
abstract public class WorkLoginController<LoginData> {
	protected final Logger logger = JFishLoggerFactory.getLogger(getClass());

	@Autowired
	private WorkQRConnectHandler workQRConnectHandler;
	@Autowired
	private WorkWechatOAuth2Hanlder workWechatOAuth2Hanlder;
	
	@RequestMapping(value="/oauth2",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LoginData oauth2(OAuth2Request oauth2Request, HttpServletRequest request, HttpServletResponse response){
		WorkUserLoginInfo workUserLoginInfo = workWechatOAuth2Hanlder.handleInController(oauth2Request, request, response);
//		logger.info("userInfoResponse: {}", workUserLoginInfo);
		if (workUserLoginInfo==null) {
			return null;
		} else {
			LoginData token = loginByWorkWechatUser(workUserLoginInfo);
			return token;
		}
	}
	
	@RequestMapping(value="/qrConnect",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public LoginData qrConnect(OAuth2Request oauth2Request, HttpServletRequest request, HttpServletResponse response){
		WorkUserLoginInfo workUserLoginInfo = workQRConnectHandler.handleInController(oauth2Request, request, response);
//		logger.info("userInfoResponse: {}", workUserLoginInfo);
		if (workUserLoginInfo==null) {
			return null;
		} else {
			LoginData token = loginByWorkWechatUser(workUserLoginInfo);
			return token;
		}
	}
	
	/****
	 * 实现具体的业务登录逻辑
	 * @author weishao zeng
	 * @param workUserLoginInfo
	 * @return
	 */
	abstract protected LoginData loginByWorkWechatUser(WorkUserLoginInfo workUserLoginInfo);
	
}
