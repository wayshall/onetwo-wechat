package org.onetwo.ext.apiclient.wechat.serve.service.impl;

import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.ext.apiclient.wechat.serve.dto.ReceiveMessage.TextMessage;
import org.onetwo.ext.apiclient.wechat.serve.service.MessageHandler;
import org.slf4j.Logger;

/**
 * @author wayshall
 * <br/>
 */
public class EmptyStringMessageHandler implements MessageHandler<TextMessage, String>{
	protected final Logger logger = JFishLoggerFactory.getLogger(this.getClass());

	@Override
	public String onMessage(TextMessage message) {
		logger.info("message: {}", message);
		return "";
	}
	
}
