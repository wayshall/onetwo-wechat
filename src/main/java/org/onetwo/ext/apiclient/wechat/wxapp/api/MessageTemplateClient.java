package org.onetwo.ext.apiclient.wechat.wxapp.api;

import javax.validation.Valid;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.UrlConst;
import org.onetwo.ext.apiclient.wechat.wxapp.request.MessageTemplateRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wayshall
 * <br/>
 */
@WechatApiClient(url=UrlConst.API_BASE_URL)
public interface MessageTemplateClient {
	
	@PostMapping(value="/message/wxopen/template/send?access_token={accessToken}", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	WechatResponse send(@PathVariable("accessToken") String accessToken, @Valid @RequestBody MessageTemplateRequest message);

}
