package org.onetwo.ext.apiclient.work;

import org.onetwo.ext.apiclient.work.basic.TicketService;
import org.onetwo.ext.apiclient.work.serve.service.WorkMessageRouterService;
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

	/****
	 * 
	 * WorkWechatUtils#mappingMessageClassesForContactChange
	 * 
	 * @author weishao zeng
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean(WorkMessageRouterService.class)
	public WorkMessageRouterService workMessageRouterService(){
		WorkMessageRouterService service = new WorkMessageRouterService();
		return service;
	}
	
}

