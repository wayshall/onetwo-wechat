package org.onetwo.ext.apiclient.wechat.serve.controller;

import java.util.Map;

import javax.validation.Valid;

import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.spring.copier.UnderlineInitBinder;
import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.dto.MessageParam;
import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageRouterService;
import org.onetwo.ext.apiclient.wechat.serve.spi.ServeEndpoint;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wayshall
 * <br/>
 */
@RestController
@RequestMapping("serve")
public class ServeController implements UnderlineInitBinder, ServeEndpoint {
	
	protected Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageRouterService messageRouterService;

	@Override
	public String auth(@Valid ServeAuthParam authRequet){
		logger.info("ServeAuthParam: {}", authRequet);
		return messageRouterService.verifyUrl(authRequet);
	}

	@Override
	public Object onMessageReceived(MessageParam msgParam, @RequestBody Map<String, Object> message){
		logger.info("msgParam: {}, body: {}", msgParam, message);
		MessageContext mc = MessageContext.builder()
											.param(msgParam)
											.messageBody(message)
											.build();
		return messageRouterService.publish(mc);
	}


}
