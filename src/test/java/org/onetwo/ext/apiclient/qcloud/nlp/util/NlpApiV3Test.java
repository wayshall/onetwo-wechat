package org.onetwo.ext.apiclient.qcloud.nlp.util;

import org.junit.Test;
import org.onetwo.ext.apiclient.qcloud.QCloudBaseBootTests;
import org.onetwo.ext.apiclient.qcloud.nlp.NlpProperties;
import org.onetwo.ext.apiclient.qcloud.nlp.api.NlpApiV3;
import org.onetwo.ext.apiclient.qcloud.nlp.request.SensitiveWordsRecognitionRequest;
import org.onetwo.ext.apiclient.qcloud.nlp.response.SensitiveWordsRecognitionResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class NlpApiV3Test extends QCloudBaseBootTests {
	@Autowired
	NlpProperties nlpProperties;
	
	@Autowired
	NlpApiV3 nlpApi;
	
	@Test
	public void test() {
		SensitiveWordsRecognitionRequest request = SensitiveWordsRecognitionRequest.builder()
															.region("ap-guangzhou")
															.secretId(nlpProperties.getSecretId())
															.version("2019-04-08")
															.text("打倒共产党")
															.build();
		/*SignableData signData = SignableData.builder()
											.request(request)
											.method("post")
											.host("wenzhi.api.qcloud.com")
											.path("/v2/index.php")
											.build();
		String signature = NlpSigns.signHmac(nlpProperties.getSecretKey(), signData);
//		request.setSignature(LangUtils.encodeUrl(signature));
		request.setSignature(signature);*/
		SensitiveWordsRecognitionResponse res = this.nlpApi.sensitiveWordsRecognition(request);
		System.out.println("res: " + res);
	}

}
