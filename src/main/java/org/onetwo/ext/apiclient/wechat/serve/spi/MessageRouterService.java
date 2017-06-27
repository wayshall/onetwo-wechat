package org.onetwo.ext.apiclient.wechat.serve.spi;

import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;

/**
 * @author wayshall
 * <br/>
 */
public interface MessageRouterService {

	Object publish(MessageContext message);

	/****
	 * 注册消息处理器
	 * @author wayshall
	 * @param handler
	 * @return
	 */
	MessageRouterService register(MessageHandler<?, ?> handler);
	
	MessageRouterService clearHandlers(Class<? extends Message> messageType);
	
	MessageRouterService mappingReceive(String messageType, Class<? extends Message> messageClass);
	
	
	String verifyUrl(ServeAuthParam auth);

}