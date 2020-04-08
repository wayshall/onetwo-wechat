package org.onetwo.ext.apiclient.yly.api;

import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.yly.core.YlyApiClient;
import org.onetwo.ext.apiclient.yly.request.PrintTextRequest;
import org.onetwo.ext.apiclient.yly.response.PrintTextResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author weishao zeng
 * <br/>
 */
@YlyApiClient
public interface PrintClient {

	@PostMapping(value = "/print/index", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	PrintTextResponse printText(AccessTokenInfo accessToken, PrintTextRequest request);

}
