package org.onetwo.ext.apiclient.qcloud;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@ConfigurationProperties(QCloudProperties.PREFIX)
@Data
public class QCloudProperties {

	final public static String PREFIX = "qcloud";
	
	//腾讯云账户secretId，secretKey
	String secretId;
	String secretKey;
	String region = "ap-guangzhou";
	String signMethod = ClientProfile.SIGN_TC3_256;
	
	/****
	 * https://github.com/TencentCloud/tencentcloud-sdk-java
	 * 
	 * @author weishao zeng
	 * @return
	 */
	public Credential newCredential() {
        Credential credential = new Credential(secretId, secretKey);
        return credential;
	}
	/***
	 * 创建 签名方法 v3
	 * @author weishao zeng
	 * @return
	 */
	public ClientProfile newLiveClientProfile() {
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("live.tencentcloudapi.com");
        
		ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(signMethod);
        clientProfile.setHttpProfile(httpProfile);
        
        return clientProfile;
	}
}
