package org.onetwo.ext.apiclient.wechat.wxapp.api;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.wxapp.request.MessageTemplateRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class MessageTemplateClientTest extends WechatBaseTestsAdapter {
	
	@Autowired
	MessageTemplateClient wessageTemplateClient;
	@Autowired
	AccessTokenService accessTokenService;
	@Test
	public void testSend(){
		GetAccessTokenRequest request = GetAccessTokenRequest.builder()
																.appid("wx421786e4507fbbff")
																.secret("")
															.build();
		AccessTokenInfo tokenInfo = accessTokenService.getAccessToken(request);
		System.out.println("tokenInfo:"+tokenInfo);
		String accessToken = tokenInfo.getAccessToken();
		System.out.println("accesstoken:"+accessToken);
		MessageTemplateRequest msg = MessageTemplateRequest.builder()
																.formId("1527732808820")
																.templateId("g0IlnnG7RlbphVT1vzPis2KSPJ6lb0EXZ1fsHHZrRVo")
																.page("pages/schedule/schedule")
																.touser("oznnx5A2oI-_qzZIeZ90yuzy6k_U")
																.build()
																.addData("keyword1", "测试1")
																.addData("keyword2", "2018-055-31")
																.addData("keyword3", "别忘了用我们的小程序组织你的小伙伴们一起看球哦~~~");
																
		WechatResponse res = wessageTemplateClient.send(accessToken, msg);
		System.out.println("res:"+res);
	}

}
