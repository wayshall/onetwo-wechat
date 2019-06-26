package org.onetwo.ext.apiclient.wechat.serve.spi;

import org.onetwo.ext.apiclient.wechat.serve.interceptor.MessageInterceptorChain;

/**
 * @author weishao zeng
 * <br/>
 */
public interface MessageInterceptor {

	Object intercept(MessageInterceptorChain chain);
}

