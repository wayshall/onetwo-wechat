package org.onetwo.ext.apiclient.qcloud.service;

import java.util.Map;

import org.onetwo.ext.apiclient.qcloud.api.message.MessageHeader;

/**
 * @author wayshall
 * <br/>
 */
public interface LiveMessagePublisher {

	void publish(Map<String, Object> messageBody);

	void register(Object listener);
	
	LiveMessagePublisher mapping(int eventType, Class<? extends MessageHeader> messageClass);

}