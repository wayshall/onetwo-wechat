package org.onetwo.ext.apiclient.qcloud.nlp.api;

import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.CustomResponseHandler;
import org.onetwo.common.apiclient.annotation.ApiClientInterceptor;
import org.onetwo.common.apiclient.annotation.ResponseHandler;
import org.onetwo.common.apiclient.annotation.RestApiClient;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.ext.apiclient.qcloud.api.auth.RequestSignInterceptor;
import org.onetwo.ext.apiclient.qcloud.nlp.request.SensitiveWordsRecognitionRequest;
import org.onetwo.ext.apiclient.qcloud.nlp.response.NlpV3Response;
import org.onetwo.ext.apiclient.qcloud.nlp.response.NlpV3Response.NlpV3BaseResponse;
import org.onetwo.ext.apiclient.qcloud.nlp.response.SensitiveWordsRecognitionResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 签名调试工具：https://console.cloud.tencent.com/api/explorer
 * 
 * @author weishao zeng
 * <br/>
 */
@RestApiClient(url="https://nlp.tencentcloudapi.com")
public interface NlpApiV3 {

	/****
	 * https://cloud.tencent.com/document/product/271/35501
	 * 
	 * @author weishao zeng
	 * @param request
	 * @return
	 */
	@ApiClientInterceptor(RequestSignInterceptor.class)
	@PostMapping(path="/", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//	@GetMapping(path="/", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseHandler(NlpV3ResponseHandler.class)
	SensitiveWordsRecognitionResponse sensitiveWordsRecognition(SensitiveWordsRecognitionRequest request);
	
	public static class NlpV3ResponseHandler implements CustomResponseHandler<NlpV3Response<? extends NlpV3BaseResponse>> {

		@SuppressWarnings("unchecked")
		@Override
		public Class<NlpV3Response<? extends NlpV3BaseResponse>> getResponseType(ApiClientMethod apiMethod) {
			return (Class<NlpV3Response<?>>)apiMethod.getMethodReturnType();
		}

		@Override
		public Object handleResponse(ApiClientMethod apiMethod, ResponseEntity<NlpV3Response<? extends NlpV3BaseResponse>> responseEntity) {
			NlpV3Response<?> body = responseEntity.getBody();
			if (body.getResponse().getError()!=null) {
				throw new ApiClientException(body.getResponse().getError().getMessage(), body.getResponse().getError().getCode());
			}
			return body;
		}
		
	}

}
