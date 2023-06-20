package org.onetwo.ext.apiclient.baidu.api;

import org.onetwo.ext.apiclient.baidu.annotation.BaiduApiClient;
import org.onetwo.ext.apiclient.baidu.request.LicensePlateRequest;
import org.onetwo.ext.apiclient.baidu.response.LicensePlateResponse;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author weishao zeng
 * <br/>
 */
@BaiduApiClient
public interface OcrClient {

	/****
	 * 文档：https://ai.baidu.com/ai-doc/OCR/ck3h7y191
	 * 
	 * @param accessToken
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/rest/2.0/ocr/v1/license_plate", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	LicensePlateResponse licensePlate(AccessTokenInfo accessToken, LicensePlateRequest request);

}
