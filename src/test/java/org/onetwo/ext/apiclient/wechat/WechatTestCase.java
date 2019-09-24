package org.onetwo.ext.apiclient.wechat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatOauth2ClientTest;
import org.onetwo.ext.apiclient.wechat.basic.api.WechatServerTest;
import org.onetwo.ext.apiclient.wechat.dbm.service.DbStoreAccessTokenServiceTest;
import org.onetwo.ext.apiclient.wechat.material.api.MaterialClientTest;
import org.onetwo.ext.apiclient.wechat.media.api.ImageClientTest;
import org.onetwo.ext.apiclient.wechat.media.api.MediaClientTest;
import org.onetwo.ext.apiclient.wechat.menu.api.MenuServiceTest;
import org.onetwo.ext.apiclient.wechat.user.api.UserInfoClientTest;
import org.onetwo.ext.apiclient.wechat.utils.WechatSignsTest;
import org.onetwo.ext.apiclient.wechat.wxa.api.ContentSecurityClientTest;

/**
 * @author wayshall
 * <br/>
 */

@RunWith(Suite.class)
@SuiteClasses({
	WechatSignsTest.class,
	WechatServerTest.class,
	DbStoreAccessTokenServiceTest.class,
	MenuServiceTest.class,
	WechatOauth2ClientTest.class,
	ContentSecurityClientTest.class,
	ImageClientTest.class,
	MediaClientTest.class,
	MaterialClientTest.class,
	UserInfoClientTest.class
})
public class WechatTestCase {

}
