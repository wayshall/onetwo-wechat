package org.onetwo.ext.apiclient.wechat.serve.service.impl;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

import org.onetwo.common.exception.BaseException;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.reflect.ReflectUtils;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.serve.msg.ReceiveMessage;
import org.onetwo.ext.apiclient.wechat.serve.msg.ReceiveMessage.TextMessage;
import org.onetwo.ext.apiclient.wechat.serve.service.MessageHandler;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MessageType;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MessageTypeParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.Maps;

/**
 * @author wayshall
 * <br/>
 */
public class MessageRouterServiceImpl implements InitializingBean {
	
	private Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	
	private Map<Class<? extends MessageType>, MessageHandler<ReceiveMessage, Object>> handlerMapper = Maps.newConcurrentMap();
	private Map<MessageType, Class<? extends ReceiveMessage>> messageTypeMapper = Maps.newConcurrentMap();
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		registerMessageClass(MessageType.TEXT, TextMessage.class);
	}
	
	final public MessageRouterServiceImpl registerMessageClass(MessageType msgType, Class<? extends ReceiveMessage> msgClass){
		this.messageTypeMapper.put(msgType, msgClass);
		return this;
	}

	public void publish(Object message){
		ReceiveMessage receiveMessage = converMessage(message);
		Optional<MessageHandler<ReceiveMessage, Object>> handlerOpt = findHandlerByMessage(receiveMessage);
		handlerOpt.ifPresent(h->h.onMessage(receiveMessage));
	}
	public ReceiveMessage converMessage(Object message){
		if(message instanceof Map){
			Map<String, Object> messageMap = (Map<String, Object>)message;
			String msgTypeValue = (String)messageMap.get(MessageTypeParams.MSG_TYPE);
			if(StringUtils.isBlank(msgTypeValue)){
				throw new BaseException("unknow message type:"+msgTypeValue);
			}
			MessageType type = MessageType.of(msgTypeValue);
			Class<? extends ReceiveMessage> msgClass = messageTypeMapper.get(type);
			
		}else if(message instanceof ReceiveMessage){
			return (ReceiveMessage) message;
		}else{
			throw new IllegalArgumentException("unknow message: " + message);
		}
	}
	public Optional<MessageHandler<ReceiveMessage, Object>> findHandlerByMessage(ReceiveMessage message){
		MessageHandler<ReceiveMessage, Object> handler = handlerMapper.get(message.getClass());
		return Optional.ofNullable(handler);
	}


	@SuppressWarnings("unchecked")
	public MessageRouterServiceImpl register(MessageHandler<ReceiveMessage, Object> handler) {
		Method onMessageMethod = ReflectUtils.findMethod(handler.getClass(), "onMessage", ReceiveMessage.class);
		Class<?>[] paramClasses = onMessageMethod.getParameterTypes();
		Class<? extends MessageType> mtype = (Class<? extends MessageType>)paramClasses[0];
		if(logger.isInfoEnabled()){
			logger.info("register message handler: {}, message type: {}", handler, mtype);
		}
		this.handlerMapper.put(mtype, handler);
		return this;
	}

}
