package org.onetwo.ext.apiclient.wechat.poi.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.poi.request.GetPoiListRequest;
import org.onetwo.ext.apiclient.wechat.poi.response.GetPoiListResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
/**
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1444378120
 * @author wayshall
 * <br/>
 */
@WechatApiClient
public interface PoiClient {
	
	/***
	 * 查询门店列表
	 * @author wayshall
	 * @param buffer
	 * @return
	 */
	@PostMapping(path="/poi/getpoilist", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@WechatRequestConfig(accessToken=true)
	GetPoiListResponse getPoiList(AccessTokenInfo accessToken, GetPoiListRequest buffer);

}
