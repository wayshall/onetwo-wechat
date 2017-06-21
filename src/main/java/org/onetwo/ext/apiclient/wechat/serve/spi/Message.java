package org.onetwo.ext.apiclient.wechat.serve.spi;
/**
 * @author wayshall
 * <br/>
 */
public interface Message {
	
	String getToUserName();
	String getFromUserName();
	String getMsgType();
	
	FlowType getFlowType();

	enum FlowType {
		RECEIVE,
		REPLY;
	}
}
