package org.onetwo.ext.apiclient.wechat;

import org.junit.Before;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
public class WechatBaseTestsAdapter extends WechatBaseBootTests {

	@Value("${wechat.appid}")
	protected String appid;
	@Value("${wechat.appsecret}")
	protected String appsecret;
	
	@Autowired
	AccessTokenService accessTokenService;
	
	protected AccessTokenInfo accessTokenInfo;
	    
    @Before
    public void setup(){
    	this.accessTokenInfo = getAccessToken();
    }
	
	protected AccessTokenInfo getAccessToken(){
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
																.appid(appid)
																.secret(appsecret)
															.build();
		AccessTokenInfo tokenInfo = accessTokenService.getOrRefreshAccessToken(request);
		return tokenInfo;
	}
}
