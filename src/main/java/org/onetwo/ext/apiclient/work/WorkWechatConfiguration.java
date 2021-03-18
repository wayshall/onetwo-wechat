package org.onetwo.ext.apiclient.work;

import org.onetwo.ext.apiclient.work.basic.WorkJsApiTicketService;
import org.onetwo.ext.apiclient.work.core.WorkWechatConfig;
import org.onetwo.ext.apiclient.work.serve.service.WorkMessageRouterService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author weishao zeng
 * <br/>
 */
@Configuration
@ConditionalOnProperty(value = WorkWechatConfig.ENABLED_KEY, matchIfMissing = true)
public class WorkWechatConfiguration {

	@Bean
	public WorkJsApiTicketService ticketService() {
		return new WorkJsApiTicketService();
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

