package org.onetwo.ext.apiclient.wechat.serve.dto;

import org.onetwo.ext.apiclient.wechat.serve.spi.Message;

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
	private String messageBody;
	private String decryptBody;
	
	private Message decodedMessage;

}
