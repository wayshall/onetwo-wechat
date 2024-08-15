package org.onetwo.ext.apiclient.qcloud.auth;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;

/**
 * @author weishao zeng
 * <br/>
 */
public interface CredentialProvider {
	
	Credential getCredential();
	
	ClientProfile newClientProfile(String endpoint);
	
	String getRegion();

}

