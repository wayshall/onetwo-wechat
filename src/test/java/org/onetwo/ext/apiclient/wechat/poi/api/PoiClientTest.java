package org.onetwo.ext.apiclient.wechat.poi.api;

import org.junit.Test;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.onetwo.ext.apiclient.wechat.poi.request.GetPoiListRequest;
import org.onetwo.ext.apiclient.wechat.poi.response.GetPoiListResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wayshall
 * <br/>
 */
public class PoiClientTest extends WechatBaseTestsAdapter {
	
	@Autowired
	PoiClient poiClient;
	
	@Test
	public void testGetPoiList(){
		GetPoiListRequest buffer = GetPoiListRequest.builder().build();
		GetPoiListResponse repsonse = poiClient.getPoiList(getAccessToken(), buffer);
		System.out.println("response: " + repsonse);
	}

}
