package org.onetwo.ext.apiclient.wechat.serve.spi;

import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message.ReceiveMessageType;

/**
 * @author wayshall
 * <br/>
 */
public interface MessageRouterService {

	Object publish(MessageContext message);

	/****
	 * 注册消息处理器
	 * @see org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage
	 * @author wayshall
	 * @param handler
	 * @return
	 */
	// 暂时不开放这个方法
//	MessageRouterService register(MessageHandler<?, ?> handler);
	
	
	MessageRouterService clearHandlers(Class<? extends Message> messageType);
	
	/***
	 * MessageType枚举类未定义映射的消息，可通过mapping来扩展
	 * @author wayshall
	 * @param messageType
	 * @param messageClass
	 * @param handlers
	 * @return
	 */
	// 暂时不开放这个方法
//	MessageRouterService register(String messageType, Class<? extends Message> messageClass, MessageHandler<?, ?>... handlers);
	
	/***
	 * 通过泛型handler获取messageClass，少传一个参数
	 * @author weishao zeng
	 * @param messageType
	 * @param handler
	 * @return
	 */
	MessageRouterService register(String messageType, MessageHandler<?, ?> handler);
	MessageRouterService register(String messageType, Class<? extends MessageHandler<?, ?>> handlerClass);

	MessageRouterService register(ReceiveMessageType msgType, MessageHandler<?, ?> handler);
	MessageRouterService register(ReceiveMessageType msgType, Class<? extends MessageHandler<?, ?>> handlerClass);
	
	String verifyUrl(ServeAuthParam auth);

}