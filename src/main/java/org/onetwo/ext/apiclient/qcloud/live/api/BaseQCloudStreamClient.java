package org.onetwo.ext.apiclient.qcloud.live.api;

import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.ext.apiclient.qcloud.QCloudProperties;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.tencentcloudapi.live.v20180801.LiveClient;

/**
 * @author weishao zeng
 * <br/>
 */

public class BaseQCloudStreamClient implements InitializingBean {

	protected final Logger logger = JFishLoggerFactory.getLogger(getClass());
	
	@Autowired
	private QCloudProperties qcloudProperties;
	
	protected LiveClient liveClient;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.liveClient = new LiveClient(qcloudProperties.newCredential(), qcloudProperties.getRegion(), qcloudProperties.newLiveClientProfile());
	}
}
