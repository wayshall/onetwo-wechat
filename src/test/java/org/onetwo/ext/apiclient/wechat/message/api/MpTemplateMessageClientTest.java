package org.onetwo.ext.apiclient.wechat.message.api;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.message.request.MpTemplateMessgeRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class MpTemplateMessageClientTest extends WechatBaseTestsAdapter {
	@Autowired
	MpTemplateMessageClient mpTemplateMessageClient;
	
	@Test
	public void testSend() {
		MpTemplateMessgeRequest message = MpTemplateMessgeRequest.builder()
																.touser("oA0wB1YI0nbpY0-GMZXUhv2l_ysE")
																.templateId("M2FFRC5F0pXjukVsc7TpaJZY5FEtdnP9xtuFiiFZ9SA")
																.build();
		message.addData("name", "测试1")
				.addData("remark", "2018-055-31");
		mpTemplateMessageClient.send(this.getAccessToken(), message);
	}

}

