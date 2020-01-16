package org.onetwo.ext.apiclient.qcloud.live;

import org.onetwo.ext.apiclient.qcloud.auth.CredentialProvider;
import org.onetwo.ext.apiclient.qcloud.live.endpoint.CallbackEndpoint;
import org.onetwo.ext.apiclient.qcloud.live.endpoint.impl.CallbackController;
import org.onetwo.ext.apiclient.qcloud.live.service.LiveMessagePublisher;
import org.onetwo.ext.apiclient.qcloud.live.service.StreamDataProvider;
import org.onetwo.ext.apiclient.qcloud.live.service.impl.DefaultLiveMessagePublisher;
import org.onetwo.ext.apiclient.qcloud.live.service.impl.QCloudLiveClient;
import org.onetwo.ext.apiclient.qcloud.live.service.impl.QCloudLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tencentcloudapi.common.Credential;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@ConditionalOnProperty(name=LiveProperties.ENABLE_KEY, matchIfMissing=true)
@EnableConfigurationProperties(LiveProperties.class)
public class QCloudLiveConfiguration {
	
	@Autowired
	private LiveProperties liveProperties;
	
	@Bean
	public QCloudLiveService qcloudLiveService(@Autowired StreamDataProvider streamDataProvider){
		QCloudLiveService liveService = new QCloudLiveService();
		liveService.setStreamDataProvider(streamDataProvider);
		liveService.setLiveProperties(liveProperties);
		return liveService;
	}
	
	@Bean
	@ConditionalOnMissingBean(StreamDataProvider.class)
	public StreamDataProvider streamDataProvider(){
		StreamDataProvider provider = new StreamDataProvider.DefaultStreamDataProvider();
		return provider;
	}
	
	@Bean
	@ConditionalOnMissingBean(LiveMessagePublisher.class)
	public LiveMessagePublisher liveMessagePublisher(){
		DefaultLiveMessagePublisher publisher = new DefaultLiveMessagePublisher();
		return publisher;
	}
	
	@Bean
	@ConditionalOnMissingBean(CallbackEndpoint.class)
	@ConditionalOnProperty(value=LiveProperties.CALLBACK_ENABLE_KEY, matchIfMissing=true)
	public CallbackController callbackEndpoint(){
		CallbackController callback = new CallbackController();
		return callback;
	}
	
	@Configuration
	@ConditionalOnClass(Credential.class)
	@ConditionalOnBean(CredentialProvider.class)
	protected static class QCloudClientConfiguration {
		
		@Bean
		public QCloudLiveClient qcloudLiveClient() {
			return new QCloudLiveClient();
		}
	}

}
