package org.onetwo.ext.apiclient.qqmap.api;

import org.onetwo.ext.apiclient.qqmap.QQMapUtils;
import org.onetwo.ext.apiclient.qqmap.request.ReverseAddressRequest;
import org.onetwo.ext.apiclient.qqmap.request.ReverseLocationRequest;
import org.onetwo.ext.apiclient.qqmap.response.ReverseAddressResponse;
import org.onetwo.ext.apiclient.qqmap.response.ReverseLocationResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 * @author weishao zeng
 * <br/>
 */
@WechatApiClient(url=QQMapUtils.BASE_URL)
public interface GeocoderClient {
	
	/***
	 * 根据坐标解析地址
	 * https://lbs.qq.com/webservice_v1/guide-gcoder.html
	 * 
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	@GetMapping(path="/geocoder/v1/")
	ReverseLocationResponse reverseLocation(ReverseLocationRequest request);
	
	/****
	 * 根据地址解释坐标
	 * 
	 * https://lbs.qq.com/webservice_v1/guide-geocoder.html
	 * sample：
	 * https://apis.map.qq.com/ws/geocoder/v1/?address=北京市海淀区彩和坊路海淀西大街74号&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	@GetMapping(path="/geocoder/v1/")
	ReverseAddressResponse reverseAddress(ReverseAddressRequest request);

}

