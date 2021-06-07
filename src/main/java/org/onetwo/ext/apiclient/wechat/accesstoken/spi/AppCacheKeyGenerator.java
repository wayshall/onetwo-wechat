package org.onetwo.ext.apiclient.wechat.accesstoken.spi;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;

/**
 * @author weishao zeng
 * <br/>
 */

public interface AppCacheKeyGenerator {
	
	String generated(AppidRequest appidRequest);

}
