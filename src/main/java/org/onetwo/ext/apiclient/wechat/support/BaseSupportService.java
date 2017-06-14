package org.onetwo.ext.apiclient.wechat.support;

import org.onetwo.ext.apiclient.wechat.basic.request.ServerAuthRequest;

/**
 * @author wayshall
 * <br/>
 */
public interface BaseSupportService {

	String auth(ServerAuthRequest auth);

}