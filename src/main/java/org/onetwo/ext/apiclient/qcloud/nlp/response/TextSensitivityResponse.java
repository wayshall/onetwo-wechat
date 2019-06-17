package org.onetwo.ext.apiclient.qcloud.nlp.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TextSensitivityResponse extends NlpBaseResponse {
	Double sensitive; //	Double	敏感的概率
	Double nonsensitive; //	Double	不敏感的概率
}
