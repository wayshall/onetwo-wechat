package org.onetwo.ext.apiclient.work.router;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;
import org.onetwo.ext.apiclient.wechat.serve.service.MessageRouterServiceImpl;
import org.onetwo.ext.apiclient.work.WorkWechatBaseBootTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author weishao zeng
 * <br/>
 */
@ActiveProfiles("product")
public class MessageRouterServiceImplTest extends WorkWechatBaseBootTests {
	
	@Autowired
	MessageRouterServiceImpl messageRouterService;
	
	@Test
	public void test() {
		ServeAuthParam auth = new ServeAuthParam();
		auth.setTimestamp("1558338103");
		auth.setNonce("1558920956");
		auth.setClientId("party");
		auth.setSignature("eaea5241fe8c345c4a483d3b38e156f7e77550fc");
		auth.setEchostr("3PFhq/DOj7UxUriMim4blXhj9Vxxiv63aim0cTQzZh139LRxyZASGPHPUHa+OokJ+/NL/Mpcmd7d73vsOC89Ig==");
		messageRouterService.verifyUrl(auth);
	}

}

