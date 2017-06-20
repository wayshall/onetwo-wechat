package org.onetwo.ext.apiclient.wechat.serve.service.impl;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.onetwo.common.exception.BaseException;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.reflect.ReflectUtils;
import org.onetwo.common.spring.SpringUtils;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage;
import org.onetwo.ext.apiclient.wechat.serve.service.MessageHandler;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MessageType;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MessageTypeParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author wayshall
 * <br/>
 */
@Service
public class MessageRouterServiceImpl implements InitializingBean, MessageRouterService {
	
	private Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	
	private Map<Class<? extends ReceiveMessage>, MessageHandlerMeta> handlerMapper = Maps.newConcurrentMap();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		register(new EmptyStringMessageHandler());
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object publish(MessageContext message){
		ReceiveMessage receiveMessage = convertMessageBody(message);
		List<MessageHandler> handlers = findHandlersByMessage(receiveMessage);
		if(!handlers.isEmpty()){
			//按order排序，返回第一个非null的回答消息，后续的忽略
			for(MessageHandler handler : handlers){
				Object replayMessage = handler.onMessage(receiveMessage);
				if(replayMessage!=null){
					return replayMessage;
				}
			}
		}else{
			logger.warn("handler not found for message: {}", message);
		}
		return "";
	}
	
	protected ReceiveMessage convertMessageBody(MessageContext message){
		Map<String, Object> messageMap = message.getMessageBody();
		String msgTypeValue = (String)messageMap.get(MessageTypeParams.MSG_TYPE);
		if(StringUtils.isBlank(msgTypeValue)){
			throw new BaseException("unknow message type:"+msgTypeValue);
		}
		MessageType type = MessageType.of(msgTypeValue);
		return SpringUtils.toBean(messageMap, type.getMessageClass());
	}
	
	@SuppressWarnings("rawtypes")
	protected List<MessageHandler> findHandlersByMessage(ReceiveMessage message){
		return findMessageHandlerMeta(message).map(meta->meta.getHandlers())
												.orElse(Collections.emptyList());
	}
	
	protected Optional<MessageHandlerMeta> findMessageHandlerMeta(ReceiveMessage message){
		MessageHandlerMeta meta = handlerMapper.get(message.getClass());
		return Optional.ofNullable(meta);
	}


	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MessageRouterService register(MessageHandler handler) {
		Method onMessageMethod = ReflectUtils.findMethod(handler.getClass(), method->{
			return "onMessage".equals(method.getName());
		})
		.orElseThrow(()->new RuntimeException("onMessage method nout found on class: " + handler.getClass()));
		
		Class<?>[] paramClasses = onMessageMethod.getParameterTypes();
		Class<? extends ReceiveMessage> messageClass = (Class<? extends ReceiveMessage>)paramClasses[0];
		if(logger.isInfoEnabled()){
			logger.info("register message handler: {}, message class: {}", handler, messageClass);
		}
		MessageHandlerMeta meta = this.handlerMapper.get(messageClass);
		if(meta==null){
			meta = new MessageHandlerMeta(MessageType.findByMessageClass(messageClass));
			this.handlerMapper.put(messageClass, meta);
		}
		meta.addHandler(handler);
		
		return this;
	}

	@SuppressWarnings("rawtypes")
	protected static class MessageHandlerMeta {
		final private MessageType messageType;
		final private List<MessageHandler> handlers = Lists.newArrayList();
		public MessageHandlerMeta(MessageType messageType) {
			super();
			Assert.notNull(messageType);
			this.messageType = messageType;
		}
		
		public void addHandler(MessageHandler handler){
			Assert.notNull(handler);
			this.handlers.add(handler);
			AnnotationAwareOrderComparator.sort(handlers);
		}

		public List<MessageHandler> getHandlers() {
			return handlers;
		}

		public MessageType getMessageType() {
			return messageType;
		}
		
	}
}
