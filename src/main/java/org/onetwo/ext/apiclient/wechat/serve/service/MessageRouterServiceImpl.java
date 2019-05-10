package org.onetwo.ext.apiclient.wechat.serve.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.onetwo.common.exception.BaseException;
import org.onetwo.common.jackson.JacksonXmlMapper;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.md.Hashs;
import org.onetwo.common.spring.SpringUtils;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.crypt.AesException;
import org.onetwo.ext.apiclient.wechat.crypt.WechatMsgCrypt;
import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;
import org.onetwo.ext.apiclient.wechat.serve.service.MessageMetaExtractor.MessageMeta;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message.ReceiveMessageType;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageHandler;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageRouterService;
import org.onetwo.ext.apiclient.wechat.serve.spi.Tenantable;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MessageType;
import org.onetwo.ext.apiclient.wechat.utils.WechatException;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.Assert;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.jodah.typetools.TypeResolver;

/**
 * 发布消息时，通过提取 messageType，找到映射到的对应messageBodyClass（通过receiveTypeMapper映射），
 * 通过messageBodyClass查找到对应的messageHandler（通过handlerMapper映射），并执行处理
 * @author wayshall
 * <br/>
 */
public class MessageRouterServiceImpl implements InitializingBean, MessageRouterService {
	
	private Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	

//	private MapToBeanConvertor beanConvertor = MapToBeanConvertor.builder().build();
	private Map<Class<? extends Message>, MessageHandlerMeta> handlerMapper = Maps.newConcurrentMap();
	/***
	 * 用mapper隔离messageType和messageClass，让其可扩展;
	 * 根据MsgType获取对应的消息class，并通过反射创建message对象
	 */
	private BiMap<String, Class<? extends Message>> receiveTypeMapper = HashBiMap.create();
	

	private JacksonXmlMapper jacksonXmlMapper = JacksonXmlMapper.ignoreEmpty();
//	@Autowired
//	private WechatConfig wechatConfig;
//	private WXBizMsgCrypt messageCrypt;
	@Autowired
	private WechatConfigProvider wechatConfigProvider;
	private MessageMetaExtractor messageTypeExtractor;
	@Autowired
	private ApplicationContext applicationContext;
	

	public void setMessageTypeExtractor(MessageMetaExtractor messageTypeExtractor) {
		this.messageTypeExtractor = messageTypeExtractor;
	}

	protected WechatMsgCrypt getMessageCrypt(String clientId){
		return wechatConfigProvider.getWXBizMsgCrypt(clientId);
	}
	
	public WechatConfig getWechatConfig(String clientId){
		return this.wechatConfigProvider.getWechatConfig(clientId);
	}
	
	/*public void setMessageCrypt(WXBizMsgCrypt messageCrypt) {
		this.messageCrypt = messageCrypt;
	}*/

	public void setJacksonXmlMapper(JacksonXmlMapper jacksonXmlMapper) {
		this.jacksonXmlMapper = jacksonXmlMapper;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		for(MessageType mt : MessageType.values()){
			registerBy(mt.getName(), mt.getMessageClass());
		}
		if (messageTypeExtractor==null) {
			this.messageTypeExtractor = new DefaultMessageConverter(jacksonXmlMapper);
		}
		/*try {
			if(messageCrypt==null){
				this.messageCrypt = new WXBizMsgCrypt(wechatConfig.getToken(), wechatConfig.getEncodingAESKey(), wechatConfig.getAppid());
			}
		} catch (AesException e) {
			throw new BaseException(e.getMessage(), e);
		}*/
	}

	/***
	 * 
	 * @author weishao zeng
	 * @param messageType
	 * @param handler
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T, R> MessageRouterServiceImpl register(String messageType, MessageHandler<T, R> handler){
		Class<? extends Message> messageClass = getMessageClassByHandler((Class<? extends MessageHandler<T, R>>)handler.getClass());
		return this.registerBy(messageType, messageClass, handler);
	}
	
	@SuppressWarnings("unchecked")
	public MessageRouterServiceImpl register(String messageType, Class<? extends MessageHandler<?, ?>> handlerClass){
		Class<? extends Message> messageClass = getMessageClassByHandler(handlerClass);
		this.checkAndPutMessageType(messageType, messageClass);
		List<MessageHandler<?, ?>> handler = (List<MessageHandler<?, ?>>)SpringUtils.getBeans(applicationContext, handlerClass);
		return this.registerBy(messageType, messageClass, handler.toArray(new MessageHandler<?, ?>[0]));
	}

	@SuppressWarnings("unchecked")
	public MessageRouterServiceImpl register(ReceiveMessageType msgType, Class<? extends MessageHandler<?, ?>> handlerClass){
		Class<? extends Message> messageClass = getMessageClassByHandler(handlerClass);
		if (msgType.getMessageClass()!=messageClass) {
			throw new BaseException("the message class of msgType must be same with the message class of handler");
		}
		List<MessageHandler<?, ?>> handler = (List<MessageHandler<?, ?>>)SpringUtils.getBeans(applicationContext, handlerClass);
		return this.registerBy(msgType.getName(), messageClass, handler.toArray(new MessageHandler<?, ?>[0]));
	}
	
//	@Override
	public MessageRouterServiceImpl registerBy(String messageType, Class<? extends Message> messageClass, MessageHandler<?, ?>... handlers){
		this.checkAndPutMessageType(messageType, messageClass);
		if (!LangUtils.isEmpty(handlers)) {
			registerHandlerForType(messageClass, handlers);
		}
		return this;
	}
	
	public <T extends Message> MessageRouterServiceImpl register(String messageType, Class<T> messageClass, MessageHandler<T, ?> handler) {
		return this.registerBy(messageType, messageClass, handler);
	}
	
	private void checkAndPutMessageType(String messageType, Class<? extends Message> messageClass) {
		if (receiveTypeMapper.containsKey(messageType)) {
			Class<? extends Message> registeredClass = receiveTypeMapper.get(messageType);
			// 相同的messageType，只能映射到相同的messageClass
			if (registeredClass!=messageClass) {
				throw new WechatException("message type["+messageType+"] has bean register for: " + registeredClass);
			}
		} else {
			receiveTypeMapper.put(messageType, messageClass);
		}
	}
	
	protected Class<? extends Message> getMessageClass(String messageType){
		Class<? extends Message> messageClass = receiveTypeMapper.get(messageType);
		if(messageClass==null){
			throw new WechatException("message class not found for messageType: " + messageType);
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
		List<MessageHandler> handlers = findMessageHandlerMeta(receiveMessage).map(meta->meta.getHandlers())
																				.orElse(Collections.emptyList());
		if(!handlers.isEmpty()){
			//按order排序，返回第一个非null的回答消息，后续的忽略
			for(MessageHandler handler : handlers){
				Object replyMessage = handler.onMessage(receiveMessage);
				if(logger.isInfoEnabled()){
					logger.info("receiveMessage: {}, replyMessage: {}", receiveMessage, replyMessage);
				}
				if(replyMessage!=null){
					return replyMessage(message, replyMessage);
				}
			}
		}else{
			logger.warn("handler not found for message: {}", message);
		}
		return "";
	}
	
	protected Object replyMessage(MessageContext message, Object replyMessage){
		if(message.getParam().isEncryptByAes()){
			try {
//				String replyMsg = this.objectMapper.writeValueAsString(replyMessage);
				String replyMsg = this.jacksonXmlMapper.toXml(replyMessage);
				replyMessage = getMessageCrypt(message.getParam().getClientId()).encryptMsg(replyMsg, message.getParam().getTimestamp(), message.getParam().getNonce());
			} catch (Exception e) {
				throw new BaseException("reply message error："+e.getMessage(), e);
			}
		}
		return replyMessage;
	}
	
	public String verifyUrl(ServeAuthParam auth){
		if(isValidRequest(auth)){
			return auth.getEchostr();
		}
		try {
			//经测试，实际上微信url验证不会加密
			if(getWechatConfig(auth.getClientId()).isEncryptByAes()){
				return getMessageCrypt(auth.getClientId()).verifyUrl(auth.getSignature(), auth.getTimestamp(), auth.getNonce(), auth.getEchostr());
			}
		} catch (AesException e) {
			logger.error("verifyUrl url error: "+e.getMessage(), e);
			return null;
		}
		return null;
	}
	
	private boolean isValidRequest(ServeAuthParam auth){
		List<String> authItems = new ArrayList<>();
		authItems.add(getWechatConfig(auth.getClientId()).getToken());
		authItems.add(auth.getTimestamp());
		authItems.add(auth.getNonce());
		Collections.sort(authItems);
		String source = StringUtils.join(authItems, "");
		String sha1String = Hashs.SHA.hash(source);
		return sha1String.equalsIgnoreCase(auth.getSignature());
	}
	
	/***
	 * 获取Encrypt，并解密返回
	 * @author wayshall
	 * @param message
	 * @return
	 */
	protected Map<String, Object> decodeMessageBody(MessageContext message){
		Map<String, Object> rawMap = jacksonXmlMapper.fromXml(message.getMessageBody(), Map.class);
		String encryptBody = (String)rawMap.get(WechatConstants.BODY_ENCRYPT); 
		try {
			String xml = getMessageCrypt(message.getParam().getClientId()).decryptMsg(message.getParam().getMsgSignature(), 
											message.getParam().getTimestamp(), 
											message.getParam().getNonce(), 
											encryptBody);
			Map<String, Object> messageMap = jacksonXmlMapper.fromXml(xml, Map.class);
			if(logger.isInfoEnabled()){
				logger.info("decode message: {}", messageMap);
			}
			return messageMap;
		} catch (Exception e) {
			throw new BaseException(e.getMessage(), e);
		}
	}
	
	protected void decryptBody(MessageContext message){
		if(!message.getParam().isEncryptByAes()){
			message.setDecryptBody(message.getMessageBody());
			return ;
		}
		
		/*Map<String, Object> rawMap = jacksonXmlMapper.fromXml(message.getMessageBody(), Map.class);
		String encryptBody = (String)rawMap.get(WechatConstants.BODY_ENCRYPT);*/ 
		try {
			String xml = getMessageCrypt(message.getParam().getClientId()).decryptBody(message.getParam().getMsgSignature(), 
											message.getParam().getTimestamp(), 
											message.getParam().getNonce(), 
											message.getMessageBody());
			if(logger.isInfoEnabled()){
				logger.info("decode message: {}", xml);
			}
			message.setDecryptBody(xml);
		} catch (Exception e) {
			throw new BaseException(e.getMessage(), e);
		}
	}
	
	/***
	 * 根据MsgType获取对应的消息class，并通过反射创建message对象
	 * @author weishao zeng
	 * @param msgContext
	 * @return
	 */
	protected Message convertMessageBody(MessageContext msgContext){
		decryptBody(msgContext);
		Class<? extends Message> messageClass = getMessageClass(msgContext);
//		messageMap.put("clientId", message.getParam().getClientId());
//		return beanConvertor.toBean(messageMap, messageClass);
		Message routeMessage = jacksonXmlMapper.fromXml(msgContext.getDecryptBody(), messageClass);
		if (routeMessage instanceof Tenantable) {
			Tenantable tenant = (Tenantable) routeMessage;
			tenant.setClientId(msgContext.getParam().getClientId());
		}
		return routeMessage;
	}

	protected Class<? extends Message> getMessageClass(MessageContext message){
		MessageMeta meta = this.messageTypeExtractor.extract(message);
		Class<? extends Message> messageClass = null;
		if (StringUtils.isNotBlank(meta.getType())) {
			messageClass = getMessageClass(meta.getType());
		} else {
			messageClass = meta.getMessageBodyClass();
		}
		if(messageClass==null){
			throw new WechatException("message class not found for message: " + meta);
		}
		return messageClass;
	}
	
	/***
	 * 根据MsgType获取对应的消息class，并通过反射创建message对象
	 * convert message map to bean
	 * @author wayshall
	 * @param message
	 * @return
	 
	protected Message convertMessageBody2(MessageContext message){
		Map<String, Object> messageMap = null;
		if(message.getParam().isEncryptByAes()){
//			String toUserName = (String)message.getMessageBody().get(WechatConstants.BODY_TO_USER_NAME); 
			messageMap = decodeMessageBody(message);
		}else{
			messageMap = message.getMessageBody();
		}
		messageMap.put("clientId", message.getParam().getClientId());
		String msgTypeValue = (String)messageMap.get(MessageTypeParams.MSG_TYPE);
		if(StringUtils.isBlank(msgTypeValue)){
			throw new BaseException("unknow message type:"+msgTypeValue);
		}
		Class<? extends Message> messageClass = getMessageClass(msgTypeValue);
		return beanConvertor.toBean(messageMap, messageClass);
	}
*/
	
	protected Optional<MessageHandlerMeta> findMessageHandlerMeta(Message message){
		MessageHandlerMeta meta = handlerMapper.get(message.getClass());
		return Optional.ofNullable(meta);
	}
	

	@SuppressWarnings("unchecked")
//	@Override
	public MessageRouterService register(MessageHandler<?, ?> handler) {
		Class<? extends Message> messageClass = getMessageClassByHandler((Class<? extends MessageHandler<?, ?>>)handler.getClass());
		return registerHandlerForType(messageClass, handler);
	}

	@SuppressWarnings("unchecked")
	private Class<? extends Message> getMessageClassByHandler(Class<? extends MessageHandler<?, ?>> handlerClass) {
		Class<?>[] paramClasses = TypeResolver.resolveRawArguments(MessageHandler.class, handlerClass);
		Class<? extends Message> messageClass = (Class<? extends Message>)paramClasses[0];
		return messageClass;
	}

	@Override
	public MessageRouterService register(ReceiveMessageType msgType, MessageHandler<?, ?> handler){
		return this.registerBy(msgType.getName(), msgType.getMessageClass(), handler);
	}


	public MessageRouterService registerHandlerForType(Class<? extends Message> messageClass, MessageHandler<?, ?>... handler) {
		if(logger.isInfoEnabled()){
			logger.info("register message handler: {}, message class: {}", handler, messageClass);
		}
		MessageHandlerMeta meta = this.handlerMapper.get(messageClass);
		if(meta==null){
			String typeName = receiveTypeMapper.inverse().get(messageClass);
			if (StringUtils.isBlank(typeName)) {
				typeName = messageClass.getSimpleName();
			}
			meta = new MessageHandlerMeta(typeName);
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
			Assert.notNull(messageType, "messageType not null");
			this.messageType = messageType;
		}
		
		public void addHandler(MessageHandler... handlers){
			Assert.notEmpty(handlers, "handler not null");
			this.handlers.addAll(Arrays.asList(handlers));
			AnnotationAwareOrderComparator.sort(this.handlers);
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
