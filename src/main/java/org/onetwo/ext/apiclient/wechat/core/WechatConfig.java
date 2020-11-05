package org.onetwo.ext.apiclient.wechat.core;

import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
public interface WechatConfig {

	String PREFIX  = "wechat";
	String ENABLE_MESSAGE_SERVE_KEY = PREFIX+".enableMessageServe.enabled";

	String getToken();

//	String getGrantType();

	String getAppid();

	String getAppsecret();
	
	/****
	 * 通讯录secrect
	 * @author weishao zeng
	 * @return
	 */
	String getContactSecrect();
	
	/***
	 * aeskey
	 * @author wayshall
	 * @return
	 */
	String getEncodingAESKey();
	
	/*** 
	 * 是否使用aes加密
	 * @author wayshall
	 * @return
	 */
	boolean isEncryptByAes();
	
	/***
	 * oauth2跳转url，一般会在拦截器构造，如果为空，则使用此配置
	 * @author wayshall
	 * @return
	 */
	String getOauth2RedirectUri();
	String getQrConnectRedirectUri();
	String getOauth2Scope();
	
	/***
	 * oauth2验证拦截url，默认所有
	 * @author wayshall
	 * @return
	 */
	String[] getOauth2InterceptUrls();
	
	/***
	 * 使用浏览器访问oauth2授权链接时，是否已直接抛错
	 * @author wayshall
	 * @return
	 */
	boolean isOauth2ErrorInBrowser();
	
//	Map<String, WechatAppInfo> getApps();
	
	Long getAgentId();
	
	/***
	 * for test
	 * 
	 * @author weishao zeng
	 * @return
	 */
	@Deprecated
	PayProperties getPay();
	
	String getConfig(String key);
	
	boolean isDebug();
	
	/***
	 * 是否企业微信
	 * @author weishao zeng
	 * @return
	 */
	default boolean isWorkWechat() {
		return getAgentId()!=null;
	}
	

	@Data
	public class PayProperties {
		private String merchantId;
		private String apiKey;
	}
}
