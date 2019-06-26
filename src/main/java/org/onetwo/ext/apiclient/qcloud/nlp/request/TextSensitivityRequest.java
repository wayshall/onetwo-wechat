package org.onetwo.ext.apiclient.qcloud.nlp.request;

import org.onetwo.ext.apiclient.qcloud.api.auth.AuthableRequest;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class TextSensitivityRequest extends AuthableRequest {
	String content; //	是	String	待分析的文本（只能为utf8编码）
	int type; //	是	int	区分敏感词类型:1表示色情，2表示政治
	
	@Builder
	public TextSensitivityRequest(String region, Long timestamp, Integer nonce, String secretId,
			String signature, String content, int type, String signatureMethod) {
		super("TextSensitivity", region, timestamp, nonce, secretId, signature, signatureMethod, null);
		this.content = content;
		this.type = type;
	}
	
}
