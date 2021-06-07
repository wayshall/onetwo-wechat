package org.onetwo.ext.apiclient.wechat.accesstoken.spi;

/**
 * @author weishao zeng
 * <br/>
 */

public interface AccessTokenRequest {
	
	String getAccessToken();
	
	void setAccessToken(String accessToken);
	
	String obtainAppId();

}
