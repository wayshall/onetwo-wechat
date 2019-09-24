package org.onetwo.ext.apiclient.wechat.serve.spi;

import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage;

/**
 * @author wayshall
 * <br/>
 */
public interface Message {
	
	String getToUserName();
	String getFromUserName();
	String getMsgType();
	
	FlowType getFlowType();

	public enum FlowType {
		RECEIVE,
		REPLY;
	}
	
	public interface ReceiveMessageType {
		Class<? extends ReceiveMessage> getMessageClass();
		String getName();
	}
}
