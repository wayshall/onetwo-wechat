package org.onetwo.ext.apiclient.work;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.onetwo.ext.apiclient.work.basic.TicketServiceTest;
import org.onetwo.ext.apiclient.work.basic.api.GetTokenClientTest;

/**
 * @author weishao zeng
 * <br/>
 */
@RunWith(Suite.class)
@SuiteClasses({
	TicketServiceTest.class,
	GetTokenClientTest.class
})
public class WorkWechatTestCase {

}

