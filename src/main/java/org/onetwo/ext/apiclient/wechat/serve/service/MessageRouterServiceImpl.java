package org.onetwo.ext.apiclient.wechat.serve.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.jodah.typetools.TypeResolver;

import org.onetwo.common.exception.BaseException;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.md.Hashs;
import org.onetwo.common.spring.utils.MapToBeanConvertor;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.core.WechatConfig;
import org.onetwo.ext.apiclient.wechat.crypt.AesException;
import org.onetwo.ext.apiclient.wechat.crypt.WXBizMsgCrypt;
import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage;
import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;
import org.onetwo.ext.apiclient.wechat.serve.spi.Message;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageHandler;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageRouterService;
import org.onetwo.ext.apiclient.wechat.serve.spi.WechatConfigProvider;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MessageType;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MessageTypeParams;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author wayshall
 * <br/>
 */
public class MessageRouterServiceImpl implements InitializingBean, MessageRouterService {
	
	private Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	

	private MapToBeanConvertor beanConvertor = MapToBeanConvertor.builder().build();
	private Map<Class<? extends Message>, MessageHandlerMeta> handlerMapper = Maps.newConcurrentMap();
	/***
	 * 用mapper隔离messageType和messageClass，让其可扩展;
	 * 根据MsgType获取对应的消息class，并通过反射创建message对象
	 */
	private BiMap<String, Class<? extends Message>> receiveTypeMapper = HashBiMap.create();
	

	private ObjectMapper objectMapper;
//	@Autowired
//	private WechatConfig wechatConfig;
//	private WXBizMsgCrypt messageCrypt;
	private WechatConfigProvider wechatConfigProvider;
	

	protected WXBizMsgCrypt getMessageCrypt(){
		return wechatConfigProvider.getWXBizMsgCrypt();
	}
	
	public WechatConfig getWechatConfig(){
		return this.wechatConfigProvider.getWechatConfig();
	}
	
	/*public void setMessageCrypt(WXBizMsgCrypt messageCrypt) {
		this.messageCrypt = messageCrypt;
	}*/

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		for(MessageType mt : MessageType.values()){
			mappingReceive(mt.getName(), mt.getMessageClass());
		}
//		register(new EmptyStringMessageHandler());
		if(objectMapper==null){
			objectMapper = Jackson2ObjectMapperBuilder.xml().build();
		}
		/*try {
			if(messageCrypt==null){
				this.messageCrypt = new WXBizMsgCrypt(wechatConfig.getToken(), wechatConfig.getEncodingAESKey(), wechatConfig.getAppid());
			}
		} catch (AesException e) {
			throw new BaseException(e.getMessage(), e);
		}*/
	}

	@Override
	public MessageRouterService mappingReceive(String messageType, Class<? extends Message> messageClass){
		receiveTypeMapper.put(messageType, messageClass);
		return this;
	}
	
	protected Class<? extends Message> getMessageClass(String messageType){
		Class<? extends Message> messageClass = receiveTypeMapper.get(messageType);
		if(messageClass==null){
			throw new BaseException("message class not found for messageType: " + messageType);
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
				String replyMsg = this.objectMapper.writeValueAsString(replyMessage);
				replyMessage = getMessageCrypt().encryptMsg(replyMsg, message.getParam().getTimestamp(), message.getParam().getNonce());
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
			if(getWechatConfig().isEncryptByAes()){
				return getMessageCrypt().verifyUrl(auth.getSignature(), auth.getTimestamp(), auth.getNonce(), auth.getEchostr());
			}
		} catch (AesException e) {
			logger.error("verifyUrl url error: "+e.getMessage(), e);
			return null;
		}
		return null;
	}
	
	private boolean isValidRequest(ServeAuthParam auth){
		List<String> authItems = new ArrayList<>();
		authItems.add(getWechatConfig().getToken());
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
	@SuppressWarnings("unchecked")
	protected Map<String, Object> decodeMessageBody(MessageContext message){
		String encryptBody = (String)message.getMessageBody().get(WechatConstants.BODY_ENCRYPT); 
		try {
			String xml = getMessageCrypt().decryptMsg(message.getParam().getMsgSignature(), 
											message.getParam().getTimestamp(), 
											message.getParam().getNonce(), 
											encryptBody);
			Map<String, Object> messageMap = objectMapper.readValue(xml, Map.class);
			if(logger.isInfoEnabled()){
				logger.info("decode message: {}", messageMap);
			}
			return messageMap;
		} catch (Exception e) {
			throw new BaseException(e.getMessage(), e);
		}
	}
	
	/***
	 * 根据MsgType获取对应的消息class，并通过反射创建message对象
	 * convert message map to bean
	 * @author wayshall
	 * @param message
	 * @return
	 */
	protected Message convertMessageBody(MessageContext message){
		Map<String, Object> messageMap = null;
		if(message.getParam().isEncryptByAes()){
//			String toUserName = (String)message.getMessageBody().get(WechatConstants.BODY_TO_USER_NAME); 
			messageMap = decodeMessageBody(message);
		}else{
			messageMap = message.getMessageBody();
		}
		String msgTypeValue = (String)messageMap.get(MessageTypeParams.MSG_TYPE);
		if(StringUtils.isBlank(msgTypeValue)){
			throw new BaseException("unknow message type:"+msgTypeValue);
		}
		Class<? extends Message> messageClass = getMessageClass(msgTypeValue);
		return beanConvertor.toBean(messageMap, messageClass);
	}

	
	protected Optional<MessageHandlerMeta> findMessageHandlerMeta(Message message){
		MessageHandlerMeta meta = handlerMapper.get(message.getClass());
		return Optional.ofNullable(meta);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public MessageRouterService register(MessageHandler<?, ?> handler) {
		Class<?>[] paramClasses = TypeResolver.resolveRawArguments(MessageHandler.class, handler.getClass());
		Class<? extends ReceiveMessage> messageClass = (Class<? extends ReceiveMessage>)paramClasses[0];
		return register(messageClass, handler);
	}

	@Override
	public MessageRouterService register(MessageType msgType, MessageHandler<?, ?> handler){
		return register(msgType.getMessageClass(), handler);
	}


	@SuppressWarnings({ "rawtypes" })
	public MessageRouterService register(Class<? extends ReceiveMessage> messageClass, MessageHandler handler) {
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
			Assert.notNull(messageType, "messageType not null");
			this.messageType = messageType;
		}
		
		public void addHandler(MessageHandler handler){
			Assert.notNull(handler, "handler not null");
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
