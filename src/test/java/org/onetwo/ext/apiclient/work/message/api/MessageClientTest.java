package org.onetwo.ext.apiclient.work.message.api;

import java.util.Arrays;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.work.WorkWechatBaseBootTests;
import org.onetwo.ext.apiclient.work.message.request.WorkMessageRequest;
import org.onetwo.ext.apiclient.work.message.response.SendMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class MessageClientTest extends WorkWechatBaseBootTests {
	
	@Autowired
	private MessageClient messageClient;
	
	@Test
	public void testSendText() {
		AccessTokenInfo accessToken = this.getAccessToken();
		WorkMessageRequest request = WorkMessageRequest.textBuilder()
													.agentid(getWechatConfig().getAgentId())
													.touser(Arrays.asList("ZengWeiShao"))
													.text("测试一下罗～")
													.build();
		SendMessageResponse res = this.messageClient.send(accessToken, request);
		System.out.println("res: " + res);
	}
	
	/****
	 * 如果用户不在应用的可见范围内，发送消息不会出错，但是发送结果的invaliduser会包含该用户id
	 * @author weishao zeng
	 */
	@Test
	public void testSendTextCard() {
		Long agentid = 1000015L;
		WechatConfig config = wechatConfigProvider.getWechatConfig(agentid.toString());
		AccessTokenInfo accessToken = this.getAccessToken(config);
		WorkMessageRequest request = WorkMessageRequest.textCardBuilder()
													.agentid(agentid)
													.touser(Arrays.asList("ZengWeiShao")) //, "qy0106ea30ee79ba0029a316acb3"
													.title("领奖通知")
													.description("<div class=\"gray\">2016年9月26日</div> <div class=\"normal\">恭喜你抽中iPhone 7一台，领奖码：xxxx</div><div class=\"highlight\">请于2016年10月10日前联系行政同事领取</div>")
													.url("http://zhihu.com")
													.build();
		SendMessageResponse res = this.messageClient.send(accessToken, request);
		System.out.println("res: " + res);
	}
}
