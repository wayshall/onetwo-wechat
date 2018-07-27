package org.onetwo.ext.apiclient.wechat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onetwo.boot.module.redis.RedisConfiguration;
import org.onetwo.ext.apiclient.wechat.WechatBaseBootTests.TestStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestStarter.class)
public class WechatBaseBootTests {
	
	@Autowired
	protected ApplicationContext applicationContext;

	@BeforeClass
	public static void setupClass(){
	}
	@Test
	public void contextLoads() {
	}
	
	@SpringBootApplication
	@EnableWechatClient(enableMessageServe = false)
	@Import(RedisConfiguration.class)
	public static class TestStarter {
	}

}
