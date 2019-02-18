package org.onetwo.ext.apiclient.qcloud.sms;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onetwo.ext.apiclient.qcloud.TestSmsProperties;
import org.onetwo.ext.apiclient.qcloud.sms.QCloudSmsBaseBootTests.QCloudSmsTestStarter;
import org.onetwo.ext.apiclient.qcloud.sms.service.SmsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author weishao zeng
 * <br/>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=QCloudSmsTestStarter.class)
@ActiveProfiles("product")
public class QCloudSmsBaseBootTests {

	@Autowired
	protected ApplicationContext applicationContext;

	@BeforeClass
	public static void setupClass(){
	}
	@Test
	public void contextLoads() {
	}

	@SpringBootApplication
	@EnableConfigurationProperties({SmsProperties.class, TestSmsProperties.class})
	@Import(SmsConfiguration.class)
	static public class QCloudSmsTestStarter {

	}
}

