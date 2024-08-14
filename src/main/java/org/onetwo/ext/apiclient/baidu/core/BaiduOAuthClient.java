package org.onetwo.ext.apiclient.baidu.core;

import org.onetwo.ext.apiclient.baidu.annotation.BaiduApiClient;
import org.onetwo.ext.apiclient.baidu.request.BaiduOAuthRequest;
import org.onetwo.ext.apiclient.baidu.response.BaiduOAuthResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author weishao zeng
 * <br/>
 */
@BaiduApiClient
public interface BaiduOAuthClient {
	
	/***
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/oauth/2.0/token ", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	BaiduOAuthResponse getAccessToken(BaiduOAuthRequest request);
	
}
