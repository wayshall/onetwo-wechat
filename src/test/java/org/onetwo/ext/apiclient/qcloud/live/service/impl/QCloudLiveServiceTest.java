package org.onetwo.ext.apiclient.qcloud.live.service.impl;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.common.date.DateUtils;
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
			data.setStreamId("ACTIVITY-402556534988476416");
			data.setExpiredAt(DateUtils.parse("2020-01-02 23:59:59"));
			data.setPushSafeKey("259754dde43b9884dcc0ab3521237ff1");
			return data;
		});
		System.out.println("result: " + result);
		assertThat(result.getPushUrl()).contains("ACTIVITY-402556534988476416?txSecret=d59f359278e192d76a4554096782963a&txTime=5E0E137F");
	}

}

