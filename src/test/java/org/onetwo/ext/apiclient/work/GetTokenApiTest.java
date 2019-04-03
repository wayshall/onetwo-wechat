package org.onetwo.ext.apiclient.work;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.work.basic.api.TicketClient;
import org.onetwo.ext.apiclient.work.basic.api.TicketClient.JsApiTicketResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class GetTokenApiTest extends WorkWechatBaseBootTests {
	@Autowired
	private TicketClient ticketClient;
	
	@Test
	public void testGetAccessToken() {
		AccessTokenInfo at = this.getAccessToken();
		assertThat(at).isNotNull();
		assertThat(at.getAccessToken()).isNotEmpty();
		System.out.println("at: " + at);
	}
	
	@Test
	public void testGetJsApiTicket() {
		JsApiTicketResponse res = ticketClient.getJsApiTicket(getAccessToken());
		assertThat(res).isNotNull();
		assertThat(res.getTicket()).isNotEmpty();
		System.out.println("ticket: " + res);
	}

}

