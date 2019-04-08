package org.onetwo.ext.apiclient.work;

import org.onetwo.ext.apiclient.work.basic.TicketService;
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

}
