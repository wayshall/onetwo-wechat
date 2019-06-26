package org.onetwo.ext.apiclient.qcloud.nlp.response;
/**
 * @author weishao zeng
 * <br/>
 */

import java.util.List;

import org.onetwo.ext.apiclient.qcloud.nlp.response.NlpV3Response.NlpV3BaseResponse;
import org.onetwo.ext.apiclient.qcloud.nlp.response.SensitiveWordsRecognitionResponse.SensitiveWordsData;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class SensitiveWordsRecognitionResponse extends NlpV3Response<SensitiveWordsData> {
	
	@Data
	@EqualsAndHashCode(callSuper=true)
	public static class SensitiveWordsData extends NlpV3BaseResponse {

		@JsonProperty("SensitiveWords")
		List<String> sensitiveWords;
	}

}
