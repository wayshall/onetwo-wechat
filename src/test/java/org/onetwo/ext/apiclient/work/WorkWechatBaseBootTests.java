package org.onetwo.ext.apiclient.work;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onetwo.ext.apiclient.wechat.accesstoken.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.work.core.WorkWechatConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=WorkWechatTestStarter.class)
@ActiveProfiles("product")
public class WorkWechatBaseBootTests {
	
	@Autowired
	protected ApplicationContext applicationContext;
	
	@Autowired
	AccessTokenService accessTokenService;
	@Autowired
	WorkWechatConfig workWechatConfig;
	
	protected AccessTokenInfo accessTokenInfo;

	@BeforeClass
	public static void setupClass(){
	}
	@Test
	public void contextLoads() {
	}
	    
    @Before
    public void setup(){
    	this.accessTokenInfo = getAccessToken();
    }
	
	protected AccessTokenInfo getAccessToken(){
		WechatConfig wechatConfig = workWechatConfig.getApps().get("party");
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
																.appid(wechatConfig.getAppid())
																.secret(wechatConfig.getAppsecret())
															.build();
		AccessTokenInfo tokenInfo = accessTokenService.getOrRefreshAccessToken(request);
		return tokenInfo;
	}
	
	protected AccessTokenInfo getAgentAccessToken(){
		WechatConfig wechatConfig = workWechatConfig.getApps().get("party-agent");
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
																.appid(wechatConfig.getAppid())
																.secret(wechatConfig.getAppsecret())
															.build();
		AccessTokenInfo tokenInfo = accessTokenService.getOrRefreshAccessToken(request);
		return tokenInfo;
	}
}
