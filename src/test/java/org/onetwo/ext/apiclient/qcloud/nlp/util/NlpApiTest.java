package org.onetwo.ext.apiclient.qcloud.nlp.util;

import org.junit.Test;
import org.onetwo.ext.apiclient.qcloud.QCloudBaseBootTests;
import org.onetwo.ext.apiclient.qcloud.nlp.NlpProperties;
import org.onetwo.ext.apiclient.qcloud.nlp.api.NlpApiV2;
import org.onetwo.ext.apiclient.qcloud.nlp.request.TextSensitivityRequest;
import org.onetwo.ext.apiclient.qcloud.nlp.response.TextSensitivityResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author weishao zeng
 * <br/>
 */
public class NlpApiTest extends QCloudBaseBootTests {
	@Autowired
	NlpProperties nlpProperties;
	
	@Autowired
	NlpApiV2 nlpApi;
	
	@Test
	public void test() {
		TextSensitivityRequest request = TextSensitivityRequest.builder()
															.region("ap-guangzhou")
															.secretId(nlpProperties.getSecretId())
															.content("打倒共产党")
															.type(2)
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
		TextSensitivityResponse res = this.nlpApi.textSensitivity(request);
		System.out.println("res: " + res);
	}

}
