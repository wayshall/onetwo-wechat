package org.onetwo.ext.apiclient.qcloud.trtc;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onetwo.ext.apiclient.qcloud.trtc.QCloudTrtcBaseBootTests.QCloudTrtcTestStarter;
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
@SpringBootTest(classes=QCloudTrtcTestStarter.class)
@ActiveProfiles("product")
public class QCloudTrtcBaseBootTests {

	@Autowired
	protected ApplicationContext applicationContext;

	@BeforeClass
	public static void setupClass(){
	}
	@Test
	public void contextLoads() {
	}

	@SpringBootApplication
	@EnableConfigurationProperties({TrtcProperties.class})
	@Import(TrtcConfiguration.class)
	static public class QCloudTrtcTestStarter {

	}
}

