package org.onetwo.ext.apiclient.wechat.poi.api;

import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.onetwo.ext.apiclient.wechat.core.WechatRequestConfig;
import org.onetwo.ext.apiclient.wechat.poi.request.GetPoiListRequest;
import org.onetwo.ext.apiclient.wechat.poi.response.GetPoiListResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
/**
 * @author wayshall
 * <br/>
 */
@WechatApiClient
public interface PoiClient {
	
	@PostMapping(path="/poi/getpoilist", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@WechatRequestConfig(accessToken=true)
	GetPoiListResponse getPoiList(GetPoiListRequest buffer);

}
