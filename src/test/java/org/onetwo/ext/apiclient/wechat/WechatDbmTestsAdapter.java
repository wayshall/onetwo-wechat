package org.onetwo.ext.apiclient.wechat;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onetwo.boot.module.redis.RedisConfiguration;
import org.onetwo.dbm.spring.EnableDbm;
import org.onetwo.ext.apiclient.wechat.WechatDbmTestsAdapter.WechatWithDbmTestStarter;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServer;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=WechatWithDbmTestStarter.class)
@ActiveProfiles({"product", "database"})
public class WechatDbmTestsAdapter  {

	@Autowired
	protected ApplicationContext applicationContext;

	@BeforeClass
	public static void setupClass(){
	}
	@Test
	public void contextLoads() {
	}
	
	@Value("${wechat.appid}")
	protected String appid;
	@Value("${wechat.appsecret}")
	protected String appsecret;
	
	@Autowired
	protected AccessTokenService accessTokenService;
	
	@Autowired
	protected WechatServer wechatServer;
	
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
	
	
	@SpringBootApplication
	@EnableWechatClient
	@Import(RedisConfiguration.class)
	@EnableDbm
	static public class WechatWithDbmTestStarter {

	    public static void main(String[] args) {
	        new SpringApplicationBuilder(WechatWithDbmTestStarter.class)
	        							.profiles("product", "database")
	        							.run(args);
	        														
	    }
	}
}
