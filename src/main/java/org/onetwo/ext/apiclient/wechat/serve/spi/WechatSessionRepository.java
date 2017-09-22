package org.onetwo.ext.apiclient.wechat.serve.spi;

import java.util.Optional;

import org.onetwo.ext.apiclient.wechat.serve.dto.RequestHoder;
import org.onetwo.ext.apiclient.wechat.utils.OAuth2UserInfo;

/**
 * @author wayshall
 * <br/>
 */
public interface WechatSessionRepository {

	Optional<OAuth2UserInfo> getCurrentUser(RequestHoder request);

	void saveCurrentUser(RequestHoder request, OAuth2UserInfo userInfo, boolean refresh);

	boolean checkOauth2State(RequestHoder request, String state);

	/****
	 * 生成state参数
	 * @author wayshall
	 * @param request
	 * @return
	 */
	String generateAndStoreOauth2State(RequestHoder request);

}