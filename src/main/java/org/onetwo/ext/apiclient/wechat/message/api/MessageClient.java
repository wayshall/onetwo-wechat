package org.onetwo.ext.apiclient.wechat.message.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.material.response.SendAllResponse;
import org.onetwo.ext.apiclient.wechat.message.request.SendAllRequest;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 群发接口和原创校验
 * api文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1481187827_i0l21
 * <br/>
 * @author wayshall
 * <br/>
 */
@WechatApiClient
public interface MessageClient {


    /**
     * 根据标签进行群发
     */
    @PostMapping(path = "/message/mass/sendall", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    SendAllResponse sendAll(AccessTokenInfo accessToken, @Validated @RequestBody SendAllRequest body);

}
