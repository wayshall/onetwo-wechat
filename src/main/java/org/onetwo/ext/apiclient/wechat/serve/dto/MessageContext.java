package org.onetwo.ext.apiclient.wechat.serve.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@Data
@Builder
public class MessageContext {
	
	private MessageParam param;
	private Map<String, Object> messageBody;

}
