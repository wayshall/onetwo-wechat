package org.onetwo.ext.apiclient.qcloud.live.service.impl;
/**
 * @author weishao zeng
 * <br/>
 */

import org.junit.Test;
import org.onetwo.ext.apiclient.qcloud.QCloudBaseBootTests;
import org.onetwo.ext.apiclient.qcloud.live.util.LiveStreamStates;
import org.springframework.beans.factory.annotation.Autowired;

public class QCloudLiveClientTest extends QCloudBaseBootTests {

	@Autowired
	QCloudLiveClient qcloudLiveClient;
	
	@Test
	public void testGetLiveStatus() {
		LiveStreamStates state = this.qcloudLiveClient.getLiveStatus("test");
		System.out.println("state: " + state);
	}
}

