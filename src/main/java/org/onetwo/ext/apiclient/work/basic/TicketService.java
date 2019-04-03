package org.onetwo.ext.apiclient.work.basic;

import org.onetwo.boot.module.redis.CacheData;
import org.onetwo.boot.module.redis.RedisOperationService;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.work.basic.api.TicketClient;
import org.onetwo.ext.apiclient.work.basic.api.TicketClient.JsApiTicketResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author weishao zeng
 * <br/>
 */
@Service
public class TicketService implements InitializingBean {
	@Autowired
	private TicketClient ticketClient;
	@Autowired
	private RedisOperationService redisOperationService;
	private String keyPrefix = "JsApiTicket:";
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}

	protected String getKey(String key) {
		return keyPrefix + key;
	}


	public JsApiTicketResponse getJsApiTicket(AccessTokenInfo accessToken) {
		String key = getKey(accessToken.getAppid());
		return redisOperationService.getCache(key, () -> {
			JsApiTicketResponse res = ticketClient.getJsApiTicket(accessToken);
			return CacheData.<JsApiTicketResponse>builder().value(res).build();
		});
	}
	
	public void removeByAppid(String appid) {
		String key = getKey(appid);
		redisOperationService.clear(key);
	}
	
}

