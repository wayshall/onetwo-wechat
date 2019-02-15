package org.onetwo.ext.apiclient.wechat.event;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.google.common.eventbus.EventBus;

/**
 * @author wayshall
 * <br/>
 */
public class WechatEventBus implements InitializingBean {
	final private EventBus eventBus = new EventBus("wechat-event-bus");
	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(WechatEventListener.class);
		beanMap.forEach((name, bean)->{
			register(bean);
		});
	}

	final public void register(Object listener){
		eventBus.register(listener);
	}
	
	final public void post(Object event){
		this.eventBus.post(event);
	}

	final protected EventBus getEventBus() {
		return eventBus;
	}

}
