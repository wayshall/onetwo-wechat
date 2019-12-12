package org.onetwo.ext.apiclient.qcloud.live.service.impl;

import org.onetwo.ext.apiclient.qcloud.auth.CredentialProvider;
import org.onetwo.ext.apiclient.qcloud.exception.QCloudException;
import org.onetwo.ext.apiclient.qcloud.live.LiveProperties;
import org.onetwo.ext.apiclient.qcloud.live.util.LiveStreamStates;
import org.onetwo.ext.apiclient.qcloud.util.QCloudErrors.LiveErrors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.live.v20180801.LiveClient;
import com.tencentcloudapi.live.v20180801.models.DescribeLiveStreamStateRequest;
import com.tencentcloudapi.live.v20180801.models.DescribeLiveStreamStateResponse;

/**
 * @author weishao zeng
 * <br/>
 */
public class QCloudLiveClient implements InitializingBean {
	
	private LiveClient liveClient;
	@Autowired
	private CredentialProvider credentialProvider;
	@Autowired
	private LiveProperties liveProperties;

	@Override
	public void afterPropertiesSet() throws Exception {
		Credential credential = credentialProvider.getCredential();
		liveClient = new LiveClient(credential, liveProperties.getRegion());
	}
	
	public LiveStreamStates getLiveStatus(String streamName) {
		DescribeLiveStreamStateRequest req = new DescribeLiveStreamStateRequest();
		req.setAppName(liveProperties.getAppname());
		req.setStreamName(streamName);
		req.setDomainName(liveProperties.getPushDomain());
		try {
			DescribeLiveStreamStateResponse res = liveClient.DescribeLiveStreamState(req);
			return LiveStreamStates.of(res.getStreamState());
		} catch (TencentCloudSDKException e) {
			throw new QCloudException(LiveErrors.ERR_LIVE_INVOKE_REMOTE, e);
		}
	}
}

