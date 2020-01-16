package org.onetwo.ext.apiclient.qcloud.auth;

import com.tencentcloudapi.common.Credential;

/**
 * @author weishao zeng
 * <br/>
 */
public interface CredentialProvider {
	
	Credential getCredential();

}

