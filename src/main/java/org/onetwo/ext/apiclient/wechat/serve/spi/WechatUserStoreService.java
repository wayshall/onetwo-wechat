package org.onetwo.ext.apiclient.wechat.serve.spi;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.onetwo.ext.apiclient.wechat.utils.OAuth2UserInfo;

/**
 * @author wayshall
 * <br/>
 */
public interface WechatUserStoreService {

	Optional<OAuth2UserInfo> getCurrentUser(HttpServletRequest request);

	void saveCurrentUser(HttpServletRequest request, OAuth2UserInfo userInfo, boolean refresh);

	boolean checkOauth2State(HttpServletRequest request, String state);

	/****
	 * 生成state参数
	 * @author wayshall
	 * @param request
	 * @return
	 */
	String generateAndStoreOauth2State(HttpServletRequest request);

}