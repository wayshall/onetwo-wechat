package org.onetwo.ext.apiclient.qcloud;

import org.onetwo.ext.apiclient.qcloud.auth.CredentialProvider;
import org.onetwo.ext.apiclient.qcloud.auth.DefaultCredentialProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tencentcloudapi.common.Credential;

/**
 * @author wayshall
 * <br/>
 */
@Configuration
@EnableConfigurationProperties(QCloudProperties.class)
@ConditionalOnClass(name=QCloudProperties.QCLOUD_CREDENTIAL_CLASS)
public class QCloudBaseConfiguration {
	
	@Configuration
	@ConditionalOnClass(Credential.class)
	static protected class QCloudCredentialConfiguration {
		
		@Bean
		@ConditionalOnMissingBean(CredentialProvider.class)
		public CredentialProvider qcloudCredentialProvider() {
			return new DefaultCredentialProvider();
		}

	}

}
