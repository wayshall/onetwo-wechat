package org.onetwo.ext.apiclient.work;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.onetwo.ext.apiclient.work.basic.TicketServiceTest;
import org.onetwo.ext.apiclient.work.basic.api.GetTokenClientTest;
import org.onetwo.ext.apiclient.work.contact.api.ContractClientTest;
import org.onetwo.ext.apiclient.work.contact.api.WorkUserClientTest;

/**
 * @author weishao zeng
 * <br/>
 */
@RunWith(Suite.class)
@SuiteClasses({
	TicketServiceTest.class,
	WorkUserClientTest.class,
	GetTokenClientTest.class,
	ContractClientTest.class
})
public class WorkWechatTestCase {

}

