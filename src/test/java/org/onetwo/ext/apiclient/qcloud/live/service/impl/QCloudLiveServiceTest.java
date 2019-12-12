package org.onetwo.ext.apiclient.qcloud.live.service.impl;
/**
 * @author weishao zeng
 * <br/>
 */

import org.junit.Test;
import org.onetwo.ext.apiclient.qcloud.QCloudBaseBootTests;
import org.onetwo.ext.apiclient.qcloud.live.service.StreamDataProvider.StreamData;
import org.onetwo.ext.apiclient.qcloud.live.vo.LivingResult;
import org.springframework.beans.factory.annotation.Autowired;

public class QCloudLiveServiceTest extends QCloudBaseBootTests {
	
	@Autowired
	QCloudLiveService qcloudLiveService;
	
	@Test
	public void testCreateLiving() {
		LivingResult result = this.qcloudLiveService.createLiving(() -> {
			StreamData data = new StreamData();
			data.setStreamId("test");
			return data;
		});
		System.out.println("result: " + result);
	}

}

