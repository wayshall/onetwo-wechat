package org.onetwo.ext.apiclient.wechat.wxa.api;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.wxa.api.AuthClient;
import org.onetwo.ext.apiclient.wechat.wxa.request.JscodeAuthRequest;
import org.onetwo.ext.apiclient.wechat.wxa.response.JscodeAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class AuthClientTest extends WechatBaseTestsAdapter {
	
	@Autowired
	AuthClient authClient;
	
	@Test
	public void testJscode2Session(){
		JscodeAuthRequest request = JscodeAuthRequest.builder()
													 .jsCode("test")
													 .build();
		JscodeAuthResponse res = this.authClient.jscode2session(request);
		System.out.println("res: " + res);
	}

}
