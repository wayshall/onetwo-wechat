package org.onetwo.ext.apiclient.work.serve.web;

import javax.validation.Valid;

import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.spring.copier.UnderlineInitBinder;
import org.onetwo.ext.apiclient.wechat.serve.dto.MessageContext;
import org.onetwo.ext.apiclient.wechat.utils.WechatConstants.MediaTypeKeys;
import org.onetwo.ext.apiclient.work.serve.service.WorkMessageRouterService;
import org.onetwo.ext.apiclient.work.serve.vo.request.WorkMessageParam;
import org.onetwo.ext.apiclient.work.serve.vo.request.WorkUrlVerifyRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wayshall
 * <br/>
 */
//@RestController
@RequestMapping("/workEventServe") //ww = work wechat
public class WorkEventServeController implements UnderlineInitBinder {
	
	protected Logger logger = JFishLoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WorkMessageRouterService messageRouterService;

	/****
	 * 文档： https://work.weixin.qq.com/api/doc#90000/90135/90238/%E9%AA%8C%E8%AF%81URL%E6%9C%89%E6%95%88%E6%80%A7
	 * 不能加引号，注意produces
	 * 
	 * @author weishao zeng
	 * @param clientId
	 * @param authRequet
	 * @return
	 */
	@RequestMapping(path="/{clientId}", method=RequestMethod.GET, params="echostr", produces=MediaType.TEXT_PLAIN_VALUE)
	public String auth(@PathVariable("clientId") String clientId, @Valid WorkUrlVerifyRequest authRequet) {
		authRequet.setClientId(clientId);
		logger.info("ServeAuthParam: {}", authRequet);
		return messageRouterService.verifyUrl(authRequet);
	}

	@RequestMapping(path="/{clientId}", 
					method=RequestMethod.POST, 
					consumes={MediaTypeKeys.TEXT_XML_VALUE_UTF8, MediaTypeKeys.APPLICATION_XML_VALUE_UTF8},
					produces={MediaTypeKeys.TEXT_XML_VALUE_UTF8})
//	public Object onMessageReceived(@PathVariable("clientId") String clientId, WorkMessageParam msgParam, Map<String, Object> message) {
	public Object onMessageReceived(@PathVariable("clientId") String clientId, WorkMessageParam msgParam, @RequestBody String message) {
		msgParam.setClientId(clientId);
		logger.info("msgParam: {}, body: {}", msgParam, message);
		MessageContext mc = MessageContext.builder()
											.param(msgParam)
											.messageBody(message)
											.build();
		return messageRouterService.publish(mc);
	}


}
