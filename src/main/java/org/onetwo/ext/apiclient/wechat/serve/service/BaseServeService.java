package org.onetwo.ext.apiclient.wechat.serve.service;

import org.onetwo.ext.apiclient.wechat.serve.msg.ServeAuthParam;

/**
 * @author wayshall
 * <br/>
 */
public interface BaseServeService {

	String auth(ServeAuthParam auth);

}