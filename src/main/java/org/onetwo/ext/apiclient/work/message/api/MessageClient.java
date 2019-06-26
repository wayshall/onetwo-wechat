package org.onetwo.ext.apiclient.work.message.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.onetwo.ext.apiclient.work.message.request.WorkMessageRequest;
import org.onetwo.ext.apiclient.work.message.response.SendMessageResponse;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * https://work.weixin.qq.com/api/doc#90000/90135/90236
 * 
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient
public interface MessageClient {
	
	/****
	 * 应用支持推送文本、图片、视频、文件、图文等类型
	 * 
	 * 成员ID列表（消息接收者，多个接收者用‘|’分隔，最多支持1000个）。特殊情况：指定为@all，则向该企业应用的全部成员发送
	 * 
	 * 如果部分接收人无权限或不存在，发送仍然执行，但会返回无效的部分（即invaliduser或invalidparty或invalidtag），常见的原因是接收人不在应用的可见范围内。
	 * @author weishao zeng
	 * @param accessToken
	 * @param body
	 * @return
	 */
    @PostMapping(path = "/message/send", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    SendMessageResponse send(AccessTokenInfo accessToken, @Validated @RequestBody WorkMessageRequest request);

}
