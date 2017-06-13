package org.onetwo.ext.apiclient.wechat.core;
/**
 * @author wayshall
 * <br/>
 */
public interface WechatConfig {

	public String getToken();

	public void setToken(String token);

	public String getGrantType();

	public String getAppid();

	public String getAppsecret();
	
}
