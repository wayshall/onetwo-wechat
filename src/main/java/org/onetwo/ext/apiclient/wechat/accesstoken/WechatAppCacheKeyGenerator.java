package org.onetwo.ext.apiclient.wechat.accesstoken;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.AppidRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AppCacheKeyGenerator;

/**
 * @author weishao zeng
 * <br/>
 */
public class WechatAppCacheKeyGenerator implements AppCacheKeyGenerator {

	public static final String ACCESS_TOKEN_PREFIX = "WX_ACCESSTOKEN";
	public static final String KEY_SPLITOR = ":";
	
	private String prefix;
	

	public WechatAppCacheKeyGenerator() {
		this(ACCESS_TOKEN_PREFIX);
	}
	
	public WechatAppCacheKeyGenerator(String prefix) {
		super();
		this.prefix = prefix;
	}

	@Override
	public String generated(AppidRequest appidRequest) {
		StringBuilder key = new StringBuilder();
		key.append(prefix).append(KEY_SPLITOR)
			.append(appidRequest.getAccessTokenType().name()).append(KEY_SPLITOR)
			.append(appidRequest.getAppid());
		if (appidRequest.getAgentId()!=null) {
			key.append(KEY_SPLITOR).append(appidRequest.getAgentId());
		}
		return key.toString();
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
