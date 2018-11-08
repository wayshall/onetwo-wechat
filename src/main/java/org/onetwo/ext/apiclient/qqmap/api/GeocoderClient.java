package org.onetwo.ext.apiclient.qqmap.api;

import org.onetwo.ext.apiclient.qqmap.QQMapUtils;
import org.onetwo.ext.apiclient.qqmap.request.ReverseLocationRequest;
import org.onetwo.ext.apiclient.qqmap.response.ReverseLocationResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author weishao zeng
 * <br/>
 */
@WechatApiClient(url=QQMapUtils.BASE_URL)
public interface GeocoderClient {
	
	/***
	 * 根据坐标解析地址
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	@GetMapping(path="/geocoder/v1/")
	ReverseLocationResponse reverseLocation(ReverseLocationRequest request);

}

