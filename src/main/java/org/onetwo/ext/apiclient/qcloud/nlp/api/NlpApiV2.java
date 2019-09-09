package org.onetwo.ext.apiclient.qcloud.nlp.api;

import org.onetwo.common.apiclient.annotation.ApiClientInterceptor;
import org.onetwo.common.apiclient.annotation.RestApiClient;
import org.onetwo.ext.apiclient.qcloud.api.auth.RequestSignInterceptor;
import org.onetwo.ext.apiclient.qcloud.nlp.request.TextSensitivityRequest;
import org.onetwo.ext.apiclient.qcloud.nlp.response.TextSensitivityResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 签名调试工具：https://console.cloud.tencent.com/api/explorer
 * 
 * @author weishao zeng
 * <br/>
 */
@RestApiClient(url="https://wenzhi.api.qcloud.com")
public interface NlpApiV2 {
	
	/***
	 * https://cloud.tencent.com/document/product/271/2615
	 * 识别信息的色情、政治等敏感程度。可用于敏感信息过滤，舆情监控等。
	 * 
	 * @author weishao zeng
	 * @param request
	 */
//	@GetMapping(path="/v2/index.php")
	@ApiClientInterceptor(RequestSignInterceptor.class)
	@PostMapping(path="/v2/index.php", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	TextSensitivityResponse textSensitivity(TextSensitivityRequest request);
	
}
