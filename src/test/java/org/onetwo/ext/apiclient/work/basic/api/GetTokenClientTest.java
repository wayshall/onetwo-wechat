package org.onetwo.ext.apiclient.work.basic.api;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.work.WorkWechatBaseBootTests;
import org.onetwo.ext.apiclient.work.basic.TicketService;
import org.onetwo.ext.apiclient.work.basic.api.TicketClient.JsApiTicketResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class GetTokenClientTest extends WorkWechatBaseBootTests {
	@Autowired
	private TicketService ticketService;
	
	@Test
	public void testGetAccessToken() {
		AccessTokenInfo at = this.getAccessToken();
		assertThat(at).isNotNull();
		assertThat(at.getAccessToken()).isNotEmpty();
		System.out.println("at: " + at);
	}
	
	@Test
	public void testGetJsApiTicket() {
		JsApiTicketResponse res = ticketService.getJsApiTicket(getAccessToken());
		assertThat(res).isNotNull();
		assertThat(res.getTicket()).isNotEmpty();
		System.out.println("ticket: " + res);
	}
	
	@Test
	public void testGetAgentJsApiTicket() {
		JsApiTicketResponse res = ticketService.getAgentJsApiTicket(getAgentAccessToken());
		assertThat(res).isNotNull();
		assertThat(res.getTicket()).isNotEmpty();
		System.out.println("ticket: " + res);
	}

}

