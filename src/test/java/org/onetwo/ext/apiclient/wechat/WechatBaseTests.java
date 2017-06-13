package org.onetwo.ext.apiclient.wechat;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onetwo.common.propconf.Environment;
import org.onetwo.common.spring.config.JFishProfile;
import org.onetwo.ext.apiclient.wechat.WechatBaseTests.WechatBaseTestInnerContextConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=WechatBaseTestInnerContextConfig.class)
@ActiveProfiles(Environment.PRODUCT)
public class WechatBaseTests {
	
	@Autowired
	protected ApplicationContext applicationContext;

	@BeforeClass
	public static void setupClass(){
	}
	@Test
	public void contextLoads() {
	}
	
	@Configuration
	@JFishProfile
	@EnableWechatClient
	public static class WechatBaseTestInnerContextConfig {
	}

}
