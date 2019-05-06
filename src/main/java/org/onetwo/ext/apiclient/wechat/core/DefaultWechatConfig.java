package org.onetwo.ext.apiclient.wechat.core;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.ext.apiclient.wechat.utils.WechatAppInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.AccessTokenStorers;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.Oauth2Keys;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.WechatConfigKeys;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@ConfigurationProperties(DefaultWechatConfig.PREFIX)
@Data
public class DefaultWechatConfig implements WechatConfig{
//	public static final String PREFIX  = "wechat";
//	public static final String SECURITY_OAUTH2_ENABLED_KEY = PREFIX + ".security.oauth2.enabled";
	
//	@Value("${wechat.token}")
	private String token;
	
//	@Value("${wechat.grantType:"+GrantTypeKeys.CLIENT_CREDENTIAL+"}")
//	private String grantType = GrantTypeKeys.CLIENT_CREDENTIAL;
	
//	@Value("${wechat.appid}")
	private String appid;
	
//	@Value("${wechat.appsecret}")
	private String appsecret;
	private String contactSecrect;
	private String agentId;
	
//	@Value("${wechat.encodingAESKey:}")
	private String encodingAESKey = "";
	
	private Oauth2Properties oauth2 = new Oauth2Properties();
	private AccessTokenProperties accessToken = new AccessTokenProperties();
	
	private Map<String, WechatAppInfo> apps = Maps.newHashMap();
	
	private PayProperties pay = new PayProperties();

	public boolean isEncryptByAes(){
		return StringUtils.isNotBlank(encodingAESKey);
	}

	@Override
	public String getOauth2RedirectUri() {
		return oauth2.getRedirectUri();
	}

	@Override
	public String getOauth2Scope() {
		return oauth2.getScope();
	}

	@Override
	public String[] getOauth2InterceptUrls() {
		return oauth2.getInterceptUrls();
	}

	@Override
	public boolean isOauth2ErrorInBrowser() {
		return oauth2.isErrorInBrowser();
	}

	@Data
	public static class Oauth2Properties {
		public static final String ENABLED_KEY = WechatConfigKeys.ENABLED_OAUTH2_KEY;
		
		/****
		 * 前端是单页引用的时候，此处可配置前端的登录页链接，让前端拿到code后调用后端api进行登录
		 */
//		@Value("${wechat.oauth2.redirectUri:}")
		private String redirectUri = "";
		private String qrConnectRedirectUri;
//		@Value("${wechat.oauth2.scope:"+Oauth2Keys.SCOPE_SNSAPI_USERINFO+"}")
		private String scope = Oauth2Keys.SCOPE_SNSAPI_USERINFO;
//		@Value("${wechat.oauth2.intercept.urls:}")
		private String[] interceptUrls;
//		@Value("${wechat.oauth2.errorInBrowser:true}")
		private boolean errorInBrowser = true;
	}
	
	@Data
	public static class AccessTokenProperties {
		AccessTokenStorers storer = AccessTokenStorers.MEMORY;
	}

	@Override
	public String getAgentId() {
		return agentId;
	}

	@Override
	public String getQrConnectRedirectUri() {
		return oauth2.getQrConnectRedirectUri();
	}
	
}
