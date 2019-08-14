package org.onetwo.ext.apiclient.qcloud;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onetwo.ext.apiclient.qcloud.base.QCloudBaseTestStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author weishao zeng
 * <br/>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=QCloudBaseTestStarter.class)
@ActiveProfiles("product")
public class QCloudBaseBootTests {

	@Autowired
	protected ApplicationContext applicationContext;

	@BeforeClass
	public static void setupClass(){
	}
	@Test
	public void contextLoads() {
	}

}

