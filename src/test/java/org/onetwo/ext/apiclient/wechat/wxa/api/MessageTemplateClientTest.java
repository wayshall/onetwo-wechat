package org.onetwo.ext.apiclient.wechat.wxa.api;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.basic.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.AccessTokenService;
import org.onetwo.ext.apiclient.wechat.message.request.MpTemplateMessge.MiniprogramData;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.wxa.request.MessageTemplateRequest;
import org.onetwo.ext.apiclient.wechat.wxa.request.UniformMessageRequest;
import org.onetwo.ext.apiclient.wechat.wxa.request.UniformMessageRequest.MpTemplateMessgeVO;
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
																.appid(appid)
																.secret("")
															.build();
		AccessTokenInfo tokenInfo = accessTokenService.getOrRefreshAccessToken(request);
		System.out.println("tokenInfo:"+tokenInfo);
		String accessToken = tokenInfo.getAccessToken();
		System.out.println("accesstoken:"+accessToken);
		MessageTemplateRequest msg = MessageTemplateRequest.builder()
																.formId("1527732808820")
																.templateId("")
																.page("pages/schedule/schedule")
																.touser("oznnx5A2oI-_qzZIeZ90yuzy6k_U")
																.build()
																.addData("keyword1", "测试1")
																.addData("keyword2", "2018-055-31")
																.addData("keyword3", "别忘了用我们的小程序组织你的小伙伴们一起看球哦~~~");
																
		WechatResponse res = wessageTemplateClient.send(tokenInfo, msg);
		System.out.println("res:"+res);
	}
	
	@Test
	public void testUniformSend(){
		MpTemplateMessgeVO msg = MpTemplateMessgeVO.builder()
													.templateId("M2FFRC5F0pXjukVsc7TpaJZY5FEtdnP9xtuFiiFZ9SA")
													.appid("wx8480fcb2bf4635e1")//公众号appid
													.miniprogram(
																MiniprogramData.builder()
																				.appid(appid) //小程序appid
																				.pagepath("pages/home/map/main")
																				.build()
																)
													.build();
		msg.addData("name", "测试1")
			.addData("remark", "2018-055-31");
		
		UniformMessageRequest reuqest = UniformMessageRequest.builder()
															.touser("oF0cE5qppA9lXT9y5Zdse72XXG8E") // 小程序的用户openid，也可以是公众号用户的openid
															.mpTemplateMsg(msg)
															.build();
		WechatResponse res = wessageTemplateClient.uniformSend(getAccessToken(), reuqest);
		System.out.println("res:"+res);
	}

}
