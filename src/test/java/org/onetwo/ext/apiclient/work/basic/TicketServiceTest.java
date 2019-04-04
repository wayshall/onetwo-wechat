package org.onetwo.ext.apiclient.work.basic;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.work.basic.TicketService;
import org.onetwo.ext.apiclient.work.basic.request.JsApiSignatureRequest;
import org.onetwo.ext.apiclient.work.basic.response.JsApiSignatureResponse;

public class TicketServiceTest {
	
	@Test
	public void testSignature() {
		JsApiSignatureRequest request = JsApiSignatureRequest.builder()
															.timestamp(1414587457L)
															.url("http://mp.weixin.qq.com?params=value")
															.noncestr("Wm3WZYTPz0wzccnW")
															.build();
		String ticket = "sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg";
		JsApiSignatureResponse res = TicketService.signature(ticket, request);
		assertThat(res).isNotNull();
		assertThat(res.getSignature()).isEqualTo("0f9de62fce790f9a083d5c99e95740ceb90c27ed");
	}

}

