package org.onetwo.ext.apiclient.qqmap.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.ext.apiclient.qqmap.request.ReverseLocationRequest;
import org.onetwo.ext.apiclient.qqmap.response.ReverseLocationResponse;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author weishao zeng
 * <br/>
 */
public class GeocoderClientTest extends WechatBaseTestsAdapter {
	@Value("${wechat.qqmap.key}")
	String key;
	
	@Autowired
	private GeocoderClient geocoderClient;
	
	@Test
	public void testReverseLocation() {
		ReverseLocationRequest request = ReverseLocationRequest.builder()
																.key(key)
																.location("22.84384,108.31352")
																.build();
		ReverseLocationResponse res = this.geocoderClient.reverseLocation(request);
		System.out.println("res: " + res);
		assertThat(res.getResult().getAddressComponent().getCity()).isEqualTo("南宁市");
	}

}

