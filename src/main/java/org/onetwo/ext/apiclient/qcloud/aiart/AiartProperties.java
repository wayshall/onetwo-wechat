package org.onetwo.ext.apiclient.qcloud.aiart;

import org.onetwo.ext.apiclient.qcloud.QCloudProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

import lombok.Data;

@ConfigurationProperties(AiartProperties.PREFIX)
@Data
public class AiartProperties {
	/***
	 * wechat.qcloud.aiart
	 */
	final public static String PREFIX = QCloudProperties.PREFIX + ".aiart";
	final public static String ENABLE_KEY = PREFIX + ".enabled";
	
//	String region = "ap-guangzhou";
	String signMethod = ClientProfile.SIGN_TC3_256;

	/***
	 * 创建 签名方法 v3
	 * @author weishao zeng
	 * @return
	 */
	public ClientProfile newAiartClientProfile() {
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("live.tencentcloudapi.com");
        
		ClientProfile clientProfile = new ClientProfile();
        clientProfile.setSignMethod(signMethod);
        clientProfile.setHttpProfile(httpProfile);
        
        return clientProfile;
	}

}
