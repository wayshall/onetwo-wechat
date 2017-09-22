package org.onetwo.ext.apiclient.wechat.core;
/**
 * @author wayshall
 * <br/>
 */
public interface WechatConfig {

	String getToken();

	String getGrantType();

	String getAppid();

	String getAppsecret();
	
	String getEncodingAESKey();
	
	boolean isEncryptByAes();
	
	String getOauth2RedirectUri();
	String getOauth2Scope();
	String[] getOauth2InterceptUrls();
	
	
	boolean isOauth2ErrorInBrowser();
	
}
