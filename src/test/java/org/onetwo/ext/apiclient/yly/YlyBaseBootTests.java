package org.onetwo.ext.apiclient.yly;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onetwo.boot.module.redis.RedisConfiguration;
import org.onetwo.ext.apiclient.wechat.EnableWechatClient;
import org.onetwo.ext.apiclient.yly.YlyBaseBootTests.YlyTestStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=YlyTestStarter.class)
@ActiveProfiles({"product"})
public class YlyBaseBootTests {
	
	@Autowired
	protected ApplicationContext applicationContext;

	@BeforeClass
	public static void setupClass(){
	}
	@Test
	public void contextLoads() {
	}
	
	@SpringBootApplication
	@EnableYilianyunClient
	@EnableWechatClient
	@Import(RedisConfiguration.class)
	static public class YlyTestStarter {
	}
	
}
