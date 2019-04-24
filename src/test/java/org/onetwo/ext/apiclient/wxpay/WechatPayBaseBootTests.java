package org.onetwo.ext.apiclient.wxpay;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onetwo.boot.module.redis.RedisConfiguration;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wxpay.WechatPayBaseBootTests.WechatPayTestStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=WechatPayTestStarter.class)
@ActiveProfiles("product")
public class WechatPayBaseBootTests {
	@SpringBootApplication
	@EnableWechatPayClient
	@Import(RedisConfiguration.class)
	static public class WechatPayTestStarter {

	}
	
	
	@Autowired
	protected ApplicationContext applicationContext;
	
	@Autowired
	protected WechatConfig wechatConfig;
	
	@Value("${wechat.appid}")
	protected String appid;
	@Value("${wechat.appsecret}")
	protected String appsecret;
	

	@BeforeClass
	public static void setupClass(){
	}
	@Test
	public void contextLoads() {
	}
}
