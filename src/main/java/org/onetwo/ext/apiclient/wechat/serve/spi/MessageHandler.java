package org.onetwo.ext.apiclient.wechat.serve.spi;


/**
 * @author wayshall
 * <br/>
 */
@FunctionalInterface
public interface MessageHandler<T, R> {
	
	R onMessage(T message);

}
