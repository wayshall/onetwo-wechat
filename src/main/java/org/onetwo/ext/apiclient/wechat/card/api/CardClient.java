package org.onetwo.ext.apiclient.wechat.card.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.card.request.BatchgetRequest;
import org.onetwo.ext.apiclient.wechat.card.response.BatchgetResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.UrlConst;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 批量查询卡券
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025272
 * @author wayshall
 * <br/>
 */
@WechatApiClient(url=UrlConst.API_DOMAIN_URL)
public interface CardClient {
	
	@PostMapping(path="/card/batchget", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@WechatRequestConfig(accessToken=true)
	BatchgetResponse batchget(AccessTokenInfo accessToken, @RequestBody BatchgetRequest request);

}
