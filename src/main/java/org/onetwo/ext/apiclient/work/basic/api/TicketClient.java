package org.onetwo.ext.apiclient.work.basic.api;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient
public interface TicketClient {
	
	@GetMapping(path="get_jsapi_ticket")
	JsApiTicketResponse getJsApiTicket(AccessTokenInfo accessToken);
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public class JsApiTicketResponse extends WechatResponse {
		private String ticket;
		private int expiresIn;
	}

}

