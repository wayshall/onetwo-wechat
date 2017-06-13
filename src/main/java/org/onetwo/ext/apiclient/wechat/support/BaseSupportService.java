package org.onetwo.ext.apiclient.wechat.support;

import org.onetwo.ext.apiclient.wechat.basic.request.ServerAuthRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;

/**
 * @author wayshall
 * <br/>
 */
public interface BaseSupportService {

	AccessTokenResponse getAccessToken();

	String auth(ServerAuthRequest auth);

}