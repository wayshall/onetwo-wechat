package org.onetwo.ext.apiclient.wechat.serve.spi;

import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;

/**
 * @author wayshall
 * <br/>
 */
public interface MessageRouterService {

	Object publish(MessageContext message);

	MessageRouterService register(MessageHandler<?, ?> handler);
	
	MessageRouterService clearHandlers(Class<? extends Message> messageType);
	
	MessageRouterService mappingReceive(String messageType, Class<? extends Message> messageClass);

}