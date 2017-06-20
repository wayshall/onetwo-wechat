package org.onetwo.ext.apiclient.wechat.serve.service.impl;

import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.service.MessageHandler;

/**
 * @author wayshall
 * <br/>
 */
public interface MessageRouterService {

	Object publish(MessageContext message);

	MessageRouterService register(MessageHandler<?, ?> handler);

}