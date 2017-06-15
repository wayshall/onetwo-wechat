package org.onetwo.ext.apiclient.wechat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatOauth2ClientTest;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServerTest;
import org.onetwo.ext.apiclient.wechat.menu.api.MenuServiceTest;

/**
 * @author wayshall
 * <br/>
 */

@RunWith(Suite.class)
@SuiteClasses({
	WechatServerTest.class,
	MenuServiceTest.class,
	WechatOauth2ClientTest.class
})
public class WechatTestCase {

}
