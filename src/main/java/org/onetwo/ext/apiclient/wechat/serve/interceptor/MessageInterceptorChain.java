package org.onetwo.ext.apiclient.wechat.serve.interceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Supplier;

import org.onetwo.common.utils.LangUtils;
import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageInterceptor;

/**
 * @author weishao zeng
 * <br/>
 */
public class MessageInterceptorChain {

	final private MessageContext messageContext;
	final private Supplier<Object> actualInvoker;
	private Collection<MessageInterceptor> interceptors;
	private Iterator<MessageInterceptor> iterator;
	private Object result;
	private Throwable throwable;
	

	public MessageInterceptorChain(Collection<MessageInterceptor> interceptors, MessageContext message, Supplier<Object> actualInvoker) {
		super();
		if (LangUtils.isNotEmpty(interceptors)) {
//			AnnotationAwareOrderComparator.sort(interceptors);
			this.interceptors = new LinkedList<>(interceptors);
		} else {
			this.interceptors = Collections.emptyList();
		}
		this.actualInvoker = actualInvoker;
		this.messageContext = message;
	}

	public Object invoke(){
		if(iterator==null){
			this.iterator = this.interceptors.iterator();
		}
		if(iterator.hasNext()){
			MessageInterceptor interceptor = iterator.next();
			result = interceptor.intercept(this);
		}else{
			result = actualInvoker.get();
		}
		return result;
	}
	
	Object getResult() {
		return result;
	}
	
	Throwable getThrowable() {
		return throwable;
	}

	public MessageContext getMessageContext() {
		return messageContext;
	}
}

