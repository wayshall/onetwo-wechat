package org.onetwo.ext.apiclient.work.basic.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档参考：
 * https://work.weixin.qq.com/api/doc#10029/%E8%8E%B7%E5%8F%96%E5%BA%94%E7%94%A8%E7%9A%84jsapi_ticket
 * 
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient
public interface TicketClient {
	String TYPE_AGENT_CONFIG = "agent_config";
	
	@GetMapping(path="get_jsapi_ticket")
	JsApiTicketResponse getJsApiTicket(AccessTokenInfo accessToken);
	
	/****
	 * type=agent_config
	 * @author weishao zeng
	 * @param accessToken
	 * @param type
	 * @return
	 */
	@GetMapping(path="/ticket/get")
	JsApiTicketResponse getAgentJsApiTicket(AccessTokenInfo accessToken, String type);
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public class JsApiTicketResponse extends WechatResponse {
		/***
		 * 生成签名所需的jsapi_ticket，最长为512字节
		 */
		private String ticket;
		private long expiresIn;
	}

}

