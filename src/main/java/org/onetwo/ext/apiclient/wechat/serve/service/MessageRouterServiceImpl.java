package org.onetwo.ext.apiclient.wechat.serve.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.onetwo.common.exception.BaseException;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.reflect.TypeResolver;
import org.onetwo.common.spring.utils.MapToBeanConvertor;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageHandler;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageRouterService;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MessageType;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MessageTypeParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author wayshall
 * <br/>
 */
@Service
public class MessageRouterServiceImpl implements InitializingBean, MessageRouterService {
	
	private Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	

	private MapToBeanConvertor beanConvertor = MapToBeanConvertor.builder().build();
	private Map<Class<? extends Message>, MessageHandlerMeta> handlerMapper = Maps.newConcurrentMap();
	/***
	 * 用mapper隔离messageType和messageClass，让其可扩展
	 */
	private BiMap<String, Class<? extends Message>> receiveTypeMapper = HashBiMap.create();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		for(MessageType mt : MessageType.values()){
			mappingReceive(mt.getName(), mt.getMessageClass());
		}
//		register(new EmptyStringMessageHandler());
	}

	@Override
	public MessageRouterService mappingReceive(String messageType, Class<? extends Message> messageClass){
		receiveTypeMapper.put(messageType, messageClass);
		return this;
	}
	
	protected Class<? extends Message> getMessageClass(String messageType){
		Class<? extends Message> messageClass = receiveTypeMapper.get(messageType);
		if(messageClass==null){
			throw new RuntimeException("message class not found for messageType: " + messageType);
		}
		return messageClass;
	}

	@Override
	public MessageRouterService clearHandlers(Class<? extends Message> messageType) {
		this.handlerMapper.remove(messageType);
		return this;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object publish(MessageContext message){
		Message receiveMessage = convertMessageBody(message);
		List<MessageHandler> handlers = findHandlersByMessage(receiveMessage);
		if(!handlers.isEmpty()){
			//按order排序，返回第一个非null的回答消息，后续的忽略
			for(MessageHandler handler : handlers){
				Object replyMessage = handler.onMessage(receiveMessage);
				if(logger.isInfoEnabled()){
					logger.info("receiveMessage: {}, replyMessage: {}", receiveMessage, replyMessage);
				}
				if(replyMessage!=null){
					return replyMessage;
				}
			}
		}else{
			logger.warn("handler not found for message: {}", message);
		}
		return "";
	}
	
	protected Message convertMessageBody(MessageContext message){
		Map<String, Object> messageMap = message.getMessageBody();
		String msgTypeValue = (String)messageMap.get(MessageTypeParams.MSG_TYPE);
		if(StringUtils.isBlank(msgTypeValue)){
			throw new BaseException("unknow message type:"+msgTypeValue);
		}
//		MessageType type = MessageType.of(msgTypeValue);
		Class<? extends Message> messageClass = getMessageClass(msgTypeValue);
		return beanConvertor.toBean(messageMap, messageClass);
	}

	
	@SuppressWarnings("rawtypes")
	protected List<MessageHandler> findHandlersByMessage(Message message){
		return findMessageHandlerMeta(message).map(meta->meta.getHandlers())
												.orElse(Collections.emptyList());
	}
	
	protected Optional<MessageHandlerMeta> findMessageHandlerMeta(Message message){
		MessageHandlerMeta meta = handlerMapper.get(message.getClass());
		return Optional.ofNullable(meta);
	}


	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MessageRouterService register(MessageHandler handler) {
		/*Method onMessageMethod = ReflectUtils.findMethod(handler.getClass(), method->{
			return "onMessage".equals(method.getName());
		})
		.orElseThrow(()->new RuntimeException("onMessage method nout found on class: " + handler.getClass()));
		Class<?>[] paramClasses = onMessageMethod.getParameterTypes();*/
		

		Class<?>[] paramClasses = TypeResolver.resolveRawArguments(MessageHandler.class, handler.getClass());
		
		Class<? extends ReceiveMessage> messageClass = (Class<? extends ReceiveMessage>)paramClasses[0];
		if(logger.isInfoEnabled()){
			logger.info("register message handler: {}, message class: {}", handler, messageClass);
		}
		MessageHandlerMeta meta = this.handlerMapper.get(messageClass);
		if(meta==null){
			meta = new MessageHandlerMeta(receiveTypeMapper.inverse().get(messageClass));
			this.handlerMapper.put(messageClass, meta);
		}
		meta.addHandler(handler);
		
		return this;
	}

	
	@SuppressWarnings("rawtypes")
	protected static class MessageHandlerMeta {
//		final private MessageType messageType;
		final private String messageType;
		final private List<MessageHandler> handlers = Lists.newArrayList();
		public MessageHandlerMeta(String messageType) {
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

		public String getMessageType() {
			return messageType;
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected static class OrderedMessageHandler implements MessageHandler, Ordered {
		private final MessageHandler handler;
		private final int order;
		
		public OrderedMessageHandler(MessageHandler handler, int order) {
			super();
			this.handler = handler;
			this.order = order;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object onMessage(Object message) {
			return handler.onMessage(message);
		}

		@Override
		public int getOrder() {
			return order;
		}
		
	}
}
