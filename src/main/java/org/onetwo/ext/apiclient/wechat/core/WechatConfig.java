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
	
	String getOauth2RedirectUri();
	
}
