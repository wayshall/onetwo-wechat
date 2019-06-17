package org.onetwo.ext.apiclient.qcloud.nlp.util;
/**
 * @author weishao zeng
 * <br/>
 */

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.onetwo.common.annotation.FieldName;
import org.onetwo.ext.apiclient.qcloud.nlp.request.NlpBaseRequest;
import org.onetwo.ext.apiclient.qcloud.nlp.request.SignableData;
import org.onetwo.ext.apiclient.qcloud.nlp.util.NlpSigns.NlpSignTypes;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class NlpSignsTest {
	
	@Test
	public void testSign() {
		DescribeInstancesRequest request = DescribeInstancesRequest.builder()
																.action("DescribeInstances")
																.instanceIds_0("ins-09dx96dg")
																.nonce(11886)
																.region("ap-guangzhou")
																.secretId("AKIDz8krbsJ5yKBZQpn74WFkmLPx3gnPhESA")
																.signatureMethod(NlpSignTypes.HmacSHA256.getName())
																.timestamp(1465185768L)
																.build();
		SignableData signData = SignableData.builder()
											.method("get")
											.host("cvm.api.qcloud.com")
											.path("/v2/index.php")
											.request(request)
											.build();
		String signKey = "Gu5t9xGARNpq86cd98joQYCN3Cozk1qA";
		String sourceString = NlpSigns.convertToSourceString(signKey, signData);
		assertThat(sourceString).isEqualTo("GETcvm.api.qcloud.com/v2/index.php?Action=DescribeInstances&InstanceIds.0=ins-09dx96dg&Nonce=11886&Region=ap-guangzhou&SecretId=AKIDz8krbsJ5yKBZQpn74WFkmLPx3gnPhESA&SignatureMethod=HmacSHA256&Timestamp=1465185768");
		String signResult = NlpSigns.signHmac(signKey, signData, NlpSignTypes.HmacSHA256);
		System.out.println("signResult: " + signResult);
		assertThat(signResult).isEqualTo("0EEm/HtGRr/VJXTAD9tYMth1Bzm3lLHz5RCDv1GdM8s=");
		
		request.setSignatureMethod(NlpSignTypes.HmacSHA1.getName());
		String exceptedSourceString = "GETcvm.api.qcloud.com/v2/index.php?Action=DescribeInstances&InstanceIds.0=ins-09dx96dg&Nonce=11886&Region=ap-guangzhou&SecretId=AKIDz8krbsJ5yKBZQpn74WFkmLPx3gnPhESA&SignatureMethod=HmacSHA1&Timestamp=1465185768";
		sourceString = NlpSigns.convertToSourceString(signKey, signData);
		assertThat(sourceString).isEqualTo(exceptedSourceString);
		signResult = NlpSigns.signHmac(signKey, signData, NlpSignTypes.HmacSHA1);
		System.out.println("signResult: " + signResult);
		assertThat(signResult).isEqualTo("nPVnY6njQmwQ8ciqbPl5Qe+Oru4=");
	}
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	public static class DescribeInstancesRequest extends NlpBaseRequest {
//		@FieldName("SignatureMethod")
//		String signatureMethod;
		@FieldName("InstanceIds.0")
		String instanceIds_0;
		
		@Builder
		public DescribeInstancesRequest(String action, String region, Long timestamp, int nonce, String secretId,
				String signature, String signatureMethod, String instanceIds_0) {
			super(action, region, timestamp, nonce, secretId, signature, signatureMethod);
			this.instanceIds_0 = instanceIds_0;
		}
		
	}


}
