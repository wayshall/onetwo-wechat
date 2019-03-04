package org.onetwo.ext.apiclient.wechat.serve.web;

import java.util.Map;

import javax.validation.Valid;

import org.onetwo.boot.core.web.api.WebApi;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.spring.copier.UnderlineInitBinder;
import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.serve.dto.MessageParam;
import org.onetwo.ext.apiclient.wechat.serve.dto.ServeAuthParam;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageRouterService;
import org.onetwo.ext.apiclient.wechat.serve.spi.ServeEndpoint;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wayshall
 * <br/>
 */
//@RestController
@RequestMapping("wxserve")
@WebApi
public class ServeController implements UnderlineInitBinder, ServeEndpoint {
	
	protected Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MessageRouterService messageRouterService;

	@Override
	public String auth(@PathVariable("clientId") String clientId, @Valid ServeAuthParam authRequet){
		authRequet.setClientId(clientId);
		logger.info("ServeAuthParam: {}", authRequet);
		return messageRouterService.verifyUrl(authRequet);
	}

	@Override
	public Object onMessageReceived(@PathVariable("clientId") String clientId, MessageParam msgParam, @RequestBody Map<String, Object> message){
		msgParam.setClientId(clientId);
		logger.info("msgParam: {}, body: {}", msgParam, message);
		MessageContext mc = MessageContext.builder()
											.param(msgParam)
											.messageBody(message)
											.build();
		return messageRouterService.publish(mc);
	}


}
