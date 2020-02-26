package org.onetwo.ext.apiclient.qqmap.api;

import org.junit.Test;
import org.onetwo.common.jackson.JsonMapper;
import org.onetwo.ext.apiclient.qqmap.response.DistrictResponse;
import org.onetwo.ext.apiclient.wechat.WechatBaseTestsAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author weishao zeng
 * <br/>
 */
public class DistrictClientTest extends WechatBaseTestsAdapter {
	@Value("${wechat.qqmap.key}")
	String key;
	
	@Autowired
	private DistrictClient districtClient;
	
	@Test
	public void getGetAll() {
		DistrictResponse res = districtClient.getAll(key);
		System.out.println("res: " + JsonMapper.toJsonString(res));
	}
	
	@Test
	public void getGetchildren() {
		DistrictResponse res = districtClient.getchildren(key, "110000");
		System.out.println("res: " + JsonMapper.toJsonString(res));
	}

}

