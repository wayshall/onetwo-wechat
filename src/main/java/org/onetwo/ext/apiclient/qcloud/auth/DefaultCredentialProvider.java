package org.onetwo.ext.apiclient.qcloud.auth;

import org.onetwo.ext.apiclient.qcloud.QCloudProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.tencentcloudapi.common.Credential;

/**
 * @author weishao zeng
 * <br/>
 */
public class DefaultCredentialProvider implements CredentialProvider, InitializingBean {

	@Autowired
	private QCloudProperties qcloudProperties;
	private Credential credential;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		credential = new Credential(qcloudProperties.getSecretId(), qcloudProperties.getSecretKey());
	}

	@Override
	public Credential getCredential() {
		return credential;
	}

}

