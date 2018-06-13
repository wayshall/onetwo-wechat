package org.onetwo.ext.apiclient.wechat.wxa.api;

import javax.validation.Valid;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.UrlConst;
import org.onetwo.ext.apiclient.wechat.wxa.request.MessageTemplateRequest;
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

}
