package org.onetwo.ext.apiclient.work;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.core.WechatConfigProvider;
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
	@Autowired
	protected WechatConfigProvider wechatConfigProvider;
	
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
		WechatConfig wechatConfig = getWechatConfig();
		return getAccessToken(wechatConfig);
    }
	protected AccessTokenInfo getAccessToken(WechatConfig wechatConfig){
//		String corpid = WorkWechatUtils.joinCorpid(wechatConfig.getAppid(), wechatConfig.getAgentId());
		String corpid = wechatConfig.getAppid();
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
																.appid(corpid)
																.secret(wechatConfig.getAppsecret())
																.accessTokenType(AccessTokenTypes.WORK_WECHAT)
															.build();
		AccessTokenInfo tokenInfo = accessTokenService.getOrRefreshAccessToken(request);
		return tokenInfo;
	}
	
	protected AccessTokenInfo getContactAccessToken(){
		WechatConfig wechatConfig = getWechatConfig();
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
																.appid(wechatConfig.getAppid())
																.secret(wechatConfig.getContactSecrect())
																.accessTokenType(AccessTokenTypes.CONTACTS)
															.build();
		AccessTokenInfo tokenInfo = accessTokenService.getOrRefreshAccessToken(request);
		return tokenInfo;
	}
	
	protected WechatConfig getWechatConfig() {
		WechatConfig wechatConfig = workWechatConfig.getApps().get("party");
		return wechatConfig;
	}
	
/*	protected AccessTokenInfo getAgentAccessToken(){
		WechatConfig wechatConfig = workWechatConfig.getApps().get("party-agent");
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
																.appid(wechatConfig.getAppid())
																.secret(wechatConfig.getAppsecret())
																.accessTokenType(AccessTokenTypes.WORK_AGENT)
															.build();
		AccessTokenInfo tokenInfo = accessTokenService.getOrRefreshAccessToken(request);
		return tokenInfo;
	}*/
}
