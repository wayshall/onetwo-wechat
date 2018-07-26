package org.onetwo.ext.apiclient.wechat.core;

import java.util.Map;

import org.onetwo.ext.apiclient.wechat.utils.WechatAppInfo;

/**
 * @author wayshall
 * <br/>
 */
public interface WechatConfig {

	String PREFIX  = "wechat";
	String ENABLE_MESSAGE_SERVE_KEY = PREFIX+".enableMessageServe.enabled";

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
	
	Map<String, WechatAppInfo> getApps();
	
}
