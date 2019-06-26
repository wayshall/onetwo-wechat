package org.onetwo.ext.apiclient.wechat.wxa.api;

import javax.validation.Valid;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.UrlConst;
import org.onetwo.ext.apiclient.wechat.wxa.request.MessageTemplateRequest;
import org.onetwo.ext.apiclient.wechat.wxa.request.UniformMessageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wayshall
 * <br/>
 */
@WechatApiClient(url=UrlConst.API_BASE_URL)
public interface MessageTemplateClient {
	
	@PostMapping(value="/message/wxopen/template/send", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	WechatResponse send(AccessTokenInfo accessToken, @Valid @RequestBody MessageTemplateRequest message);
	
	/****
	 * 下发小程序和公众号统一的服务消息
	 * https://developers.weixin.qq.com/miniprogram/dev/api/sendUniformMessage.html
	 * 
	 * @author weishao zeng
	 * @param accessToken 小程序的accesstoken，不能使用公众号的，因为这个接口的初衷就是想大家在开发小程序的时候，如果要发送公众号消息直接使用这个接口就可以了，无需再去调用公众号的模板消息接口。
	 * @param message
	 * @return
	 */
	@PostMapping(value="/message/wxopen/template/uniform_send", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	WechatResponse uniformSend(AccessTokenInfo accessToken, @Valid @RequestBody UniformMessageRequest message);

}
