package org.onetwo.ext.apiclient.qcloud.live.service.impl;
/**
 * @author weishao zeng
 * <br/>
 */

import org.junit.Test;
import org.onetwo.ext.apiclient.qcloud.QCloudBaseBootTests;
import org.onetwo.ext.apiclient.qcloud.live.util.LiveStreamStates;
import org.springframework.beans.factory.annotation.Autowired;

import com.tencentcloudapi.live.v20180801.models.DescribeLiveStreamStateRequest;

public class QCloudLiveClientTest extends QCloudBaseBootTests {

	@Autowired
	QCloudLiveClient qcloudLiveClient;
	
	@Test
	public void testGetLiveStatus() {
		DescribeLiveStreamStateRequest req = new DescribeLiveStreamStateRequest();
		req.setAppName("live");
		req.setStreamName("test");
		req.setDomainName("");
		LiveStreamStates state = this.qcloudLiveClient.getLiveStatus(req);
		System.out.println("state: " + state);
	}
}

