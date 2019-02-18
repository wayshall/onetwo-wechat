package org.onetwo.ext.apiclient.qcloud.live.service.impl;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.onetwo.common.convert.Types;
import org.onetwo.common.exception.ServiceException;
import org.onetwo.common.spring.SpringUtils;
import org.onetwo.common.utils.CUtils;
import org.onetwo.ext.apiclient.qcloud.live.api.message.MessageHeader;
import org.onetwo.ext.apiclient.qcloud.live.api.message.PushMessage;
import org.onetwo.ext.apiclient.qcloud.live.api.message.RecordingMessage;
import org.onetwo.ext.apiclient.qcloud.live.api.message.ScreenShotMessage;
import org.onetwo.ext.apiclient.qcloud.live.service.LiveMessageListener;
import org.onetwo.ext.apiclient.qcloud.live.service.LiveMessagePublisher;
import org.onetwo.ext.apiclient.qcloud.live.util.LiveUtils.EventTypes;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.google.common.eventbus.EventBus;

/**
 * @author wayshall
 * <br/>
 */
@Slf4j
public class DefaultLiveMessagePublisher implements LiveMessagePublisher, InitializingBean {
	
	private final EventBus eventBus = new EventBus("live meesage publisher");
	private final Map<Integer, Class<?>> eventTypeMapping = CUtils.asMap(EventTypes.CLOSE, PushMessage.class,
																			EventTypes.PUSH, PushMessage.class,
																			EventTypes.RECORDING, RecordingMessage.class,
																			EventTypes.SCREEN_SHOT, ScreenShotMessage.class);
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		applicationContext.getBeansWithAnnotation(LiveMessageListener.class)
						  .forEach((beanName, bean)->{
								register(bean);
						  });
	}

	@Override
	public void publish(Map<String, Object> messageBody){
		Integer eventType = Types.asValue(messageBody.get("event_type"), Integer.class, null);
		if(eventType==null || !eventTypeMapping.containsKey(eventType)){
			throw new ServiceException("error event type: " + messageBody.get("event_type")).put("messageBody", messageBody);
		}
		Class<?> messageClass = eventTypeMapping.get(eventType);
		Object message = SpringUtils.toBean(messageBody, messageClass);
		this.eventBus.post(message);
	}
	
	@Override
	final public void register(Object listener){
		eventBus.register(listener);
		if(log.isInfoEnabled()){
			log.info("registed LiveMessageListener: {}", listener);
		}
	}
	
	public LiveMessagePublisher mapping(int eventType, Class<? extends MessageHeader> messageClass){
		this.eventTypeMapping.put(eventType, messageClass);
		return this;
	}

}
