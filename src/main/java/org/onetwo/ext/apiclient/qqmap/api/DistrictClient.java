package org.onetwo.ext.apiclient.qqmap.api;

import org.onetwo.ext.apiclient.qqmap.QQMapUtils;
import org.onetwo.ext.apiclient.qqmap.response.DistrictResponse;
import org.onetwo.ext.apiclient.wechat.core.WechatApiClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * https://lbs.qq.com/webservice_v1/guide-region.html
 * 
 * @author weishao zeng
 * <br/>
 */
@WechatApiClient(url=QQMapUtils.BASE_URL)
public interface DistrictClient {
	
	/****
	 * 获取全部行政区划数据。该请求为GET请求。
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	@GetMapping(path="/district/v1/list")
	DistrictResponse getAll(String key);
	
	@GetMapping(path="/district/v1/getchildren")
	DistrictResponse getchildren(String key, String id);
	
	@GetMapping(path="/district/v1/search")
	DistrictResponse search(String key, String keyword);
	
}

