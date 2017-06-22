package org.onetwo.ext.apiclient.wechat.core;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wayshall
 * <br/>
 */
//@ConfigurationProperties("wechat")
@Data
public class DefaultWechatConfig implements WechatConfig{
	@Value("${wechat.token}")
	private String token;
	
	@Value("${wechat.grantType:"+WechatConstants.GT_CLIENT_CREDENTIAL+"}")
	private String grantType;
	
	@Value("${wechat.appid}")
	private String appid;
	
	@Value("${wechat.appsecret}")
	private String appsecret;
	
	@Value("${wechat.encodingAESKey:''}")
	private String encodingAESKey;
	
	@Value("${wechat.oauth2.redirectUri:''}")
	private String oauth2RedirectUri;

	public boolean isEncryptByAes(){
		return StringUtils.isNotBlank(encodingAESKey);
	}
}
