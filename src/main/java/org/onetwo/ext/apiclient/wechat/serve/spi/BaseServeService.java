package org.onetwo.ext.apiclient.wechat.serve.spi;

import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;

/**
 * @author wayshall
 * <br/>
 */
public interface BaseServeService {

	String auth(ServeAuthParam auth);

}