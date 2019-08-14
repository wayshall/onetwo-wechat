package org.onetwo.ext.apiclient.qcloud.nlp.request;

import org.onetwo.common.annotation.FieldName;
import org.onetwo.ext.apiclient.qcloud.api.auth.AuthableRequest;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SensitiveWordsRecognitionRequest extends AuthableRequest {
	
	@FieldName("Text")
	String text;

	@Builder
	public SensitiveWordsRecognitionRequest(String region, Long timestamp, Integer nonce,
			String secretId, String signature, String signatureMethod, String version, String text) {
		super("SensitiveWordsRecognition", region, timestamp, nonce, secretId, signature, signatureMethod, version);
		this.text = text;
	}
	
}
