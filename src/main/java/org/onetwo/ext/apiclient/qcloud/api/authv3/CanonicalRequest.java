package org.onetwo.ext.apiclient.qcloud.api.authv3;

import lombok.Data;

/**
 * 参考： 
 * https://github.com/TencentCloud/tencentcloud-sdk-java/blob/master/src/main/java/com/tencentcloudapi/common/AbstractClient.java
 * 
 * @author weishao zeng
 * <br/>
 */
@Data
public class CanonicalRequest {
	
	String httpRequestMethod;
	String canonicalURI = "/";
	String canonicalQueryString;
	String canonicalHeaders;
	String signedHeaders;

}
