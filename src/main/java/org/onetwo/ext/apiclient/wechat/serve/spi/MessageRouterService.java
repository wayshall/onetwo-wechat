package org.onetwo.ext.apiclient.wechat.serve.spi;

import java.util.stream.Stream;

import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;
import org.onetwo.ext.apiclient.wechat.serve.service.MessageRouterServiceImpl;
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

	default <E extends ReceiveMessageType> MessageRouterService register(E[] messageTypes) {
		Stream.of(messageTypes).forEach(msg -> {
			register(msg.getName(), msg.getMessageClass(), null);
		});
		return this;
	}
	
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
	
	<T extends Message> MessageRouterServiceImpl register(String messageType, Class<T> messageClass, MessageHandler<T, ?> handler);
	/***
	 * 通过泛型handler获取messageClass，少传一个参数
	 * @author weishao zeng
	 * @param messageType
	 * @param handler
	 * @return
	 */
	<T, R> MessageRouterService register(String messageType, MessageHandler<T, R> handler);
	MessageRouterService register(String messageType, Class<? extends MessageHandler<?, ?>> handlerClass);

	MessageRouterService register(ReceiveMessageType msgType, MessageHandler<?, ?> handler);
	MessageRouterService register(ReceiveMessageType msgType, Class<? extends MessageHandler<?, ?>> handlerClass);
	
	/***
	 * 注册拦截器
	 * @author weishao zeng
	 * @param interceptorClass
	 * @return
	 */
	MessageRouterService registerInteceptor(Class<? extends MessageInterceptor> interceptorClass);
	/***
	 * 注册拦截器
	 * @author weishao zeng
	 * @param interceptors
	 * @return
	 */
	MessageRouterService registerInteceptor(MessageInterceptor... interceptors);
	
	String verifyUrl(ServeAuthParam auth);

}