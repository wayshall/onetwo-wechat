package org.onetwo.ext.apiclient.wechat.basic.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文档参考：
 * https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/JS-SDK.html#62
 * 
 * 签名验证工具：
 * https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=jsapisign
 * 
 * @author weishao zeng
 * <br/>
 */
@WechatApiClient
public interface JsApiTicketApi {
	String TYPE_CONFIG = "jsapi";
	
	/****
	 * type=agent_config
	 * @author weishao zeng
	 * @param accessToken
	 * @param type
	 * @return
	 */
	@GetMapping(path="/ticket/getticket")
	JsApiTicketResponse getticket(AccessTokenInfo accessToken, String type);
	
	@Data
	@EqualsAndHashCode(callSuper=false)
	public class JsApiTicketResponse extends WechatResponse {
		/***
		 * 生成签名所需的jsapi_ticket，最长为512字节
		 */
		private String ticket;
		@JsonProperty("expires_in")
		private long expiresIn;
	}

}

