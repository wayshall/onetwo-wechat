package org.onetwo.ext.apiclient.work;

import org.onetwo.ext.apiclient.wechat.serve.service.MessageRouterServiceImpl;
import org.onetwo.ext.apiclient.wechat.serve.spi.MessageRouterService;
import org.onetwo.ext.apiclient.work.basic.TicketService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author weishao zeng
 * <br/>
 */
@Component
public class WorkWechatConfiguration {

	@Bean
	public TicketService ticketService() {
		return new TicketService();
	}

	@Bean
	@ConditionalOnMissingBean(MessageRouterService.class)
	public MessageRouterService messageRouterService(){
		MessageRouterServiceImpl service = new MessageRouterServiceImpl();
		return service;
	}
	
}

