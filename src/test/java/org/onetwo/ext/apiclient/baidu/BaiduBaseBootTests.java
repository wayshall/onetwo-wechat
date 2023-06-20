package org.onetwo.ext.apiclient.baidu;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onetwo.boot.module.redis.RedisConfiguration;
import org.onetwo.ext.apiclient.baidu.BaiduBaseBootTests.BaiduTestStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=BaiduTestStarter.class)
@ActiveProfiles({"product"})
public class BaiduBaseBootTests {
	
	@Autowired
	protected ApplicationContext applicationContext;

	@BeforeClass
	public static void setupClass(){
	}
	@Test
	public void contextLoads() {
	}
	
	@SpringBootApplication
	@EnableBaiduClient
	@Import(RedisConfiguration.class)
	static public class BaiduTestStarter {
	}
	
}
