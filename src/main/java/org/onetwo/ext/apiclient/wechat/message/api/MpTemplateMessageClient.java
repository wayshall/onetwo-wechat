package org.onetwo.ext.apiclient.wechat.message.api;

import javax.validation.Valid;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.message.request.MpTemplateMessgeRequest;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.UrlConst;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wayshall
 * <br/>
 */
@WechatApiClient(url=UrlConst.API_BASE_URL)
public interface MpTemplateMessageClient {
	
	/****
	 * https://mp.weixin.qq.com/wiki?action=doc&id=mp1433751277#5
	 * 
	 * @author weishao zeng
	 * @param accessToken
	 * @param message
	 * @return
	 */
	@PostMapping(value="/message/template/send", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	WechatResponse send(AccessTokenInfo accessToken, @Valid @RequestBody MpTemplateMessgeRequest message);
	
}
