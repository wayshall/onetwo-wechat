package org.onetwo.ext.apiclient.qcloud.api.authv3;

import java.util.List;

import org.springframework.http.HttpHeaders;

import lombok.Builder;
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
	HttpHeaders canonicalHeaders;
	List<String> signedHeaders;
	String hashedRequestPayload;
	
	@Builder
	public CanonicalRequest(String httpRequestMethod, String canonicalURI, String canonicalQueryString,
			HttpHeaders canonicalHeaders, List<String> signedHeaders, String hashedRequestPayload) {
		super();
		this.httpRequestMethod = httpRequestMethod;
		this.canonicalURI = canonicalURI;
		this.canonicalQueryString = canonicalQueryString;
		this.canonicalHeaders = canonicalHeaders;
		this.signedHeaders = signedHeaders;
		this.hashedRequestPayload = hashedRequestPayload;
	}
	
}
