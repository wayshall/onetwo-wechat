package org.onetwo.ext.apiclient.work.basic;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.onetwo.boot.module.redis.CacheData;
import org.onetwo.boot.module.redis.RedisOperationService;
import org.onetwo.common.date.DateUtils;
import org.onetwo.common.expr.ExpressionFacotry;
import org.onetwo.common.log.JFishLoggerFactory;
import org.onetwo.common.md.Hashs;
import org.onetwo.common.spring.SpringUtils;
import org.onetwo.common.spring.copier.CopyUtils;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.wechat.accesstoken.response.AccessTokenInfo;
import org.onetwo.ext.apiclient.work.basic.api.TicketClient;
import org.onetwo.ext.apiclient.work.basic.api.TicketClient.JsApiTicketResponse;
import org.onetwo.ext.apiclient.work.basic.request.JsApiSignatureRequest;
import org.onetwo.ext.apiclient.work.basic.response.JsApiSignatureResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author weishao zeng
 * <br/>
 */
public class TicketService implements InitializingBean {
	private static final String SIGNATURE_TEMPLATE = "jsapi_ticket=${ticket}&noncestr=${noncestr}&timestamp=${timestamp}&url=${url}";
	static private final Logger logger = JFishLoggerFactory.getLogger(TicketService.class);
	
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

	/***
	 * 获取企业jsapi ticket
	 * @author weishao zeng
	 * @param accessToken
	 * @return
	 */
	public JsApiTicketResponse getCropJsApiTicket(AccessTokenInfo accessToken) {
		String key = getKey("config:"+accessToken.getAppid());
		return redisOperationService.getCache(key, () -> {
			JsApiTicketResponse res = ticketClient.getJsApiTicket(accessToken);
			return CacheData.<JsApiTicketResponse>builder().
												value(res)
												.expire(res.getExpiresIn())
												.timeUnit(TimeUnit.SECONDS)
												.build();
		});
	}
	
	/***
	 * 获取应用jsapi ticket
	 * @author weishao zeng
	 * @param accessToken
	 * @return
	 */
	public JsApiTicketResponse getAgentJsApiTicket(AccessTokenInfo accessToken) {
		String key = getKey(TicketClient.TYPE_AGENT_CONFIG + ":"+accessToken.getAppid());
		return redisOperationService.getCache(key, () -> {
			JsApiTicketResponse res = ticketClient.getAgentJsApiTicket(accessToken, TicketClient.TYPE_AGENT_CONFIG);
			return CacheData.<JsApiTicketResponse>builder().
												value(res)
												.expire(res.getExpiresIn())
												.timeUnit(TimeUnit.SECONDS)
												.build();
		});
	}
	
	public JsApiSignatureResponse getCropSignature(AccessTokenInfo accessToken, JsApiSignatureRequest request) {
		JsApiTicketResponse ticketRes = getCropJsApiTicket(accessToken);
		return signature(ticketRes.getTicket(), request);
	}
	
	public JsApiSignatureResponse getAgentSignature(AccessTokenInfo accessToken, JsApiSignatureRequest request) {
		JsApiTicketResponse ticketRes = getAgentJsApiTicket(accessToken);
		return signature(ticketRes.getTicket(), request);
	}
	
	static public JsApiSignatureResponse signature(String ticket, JsApiSignatureRequest request) {
		Assert.notNull(request.getUrl(), "url can not be null");
//		Assert.notNull(request.getAppId(), "appid can not be null");
		if (request.getTimestamp()==null) {
			request.setTimestamp(DateUtils.now().getTime()/1000);
		}
		if (StringUtils.isBlank(request.getNoncestr())) {
			request.setNoncestr(RandomStringUtils.randomAscii(16));
		}
		
		Map<String, Object> context = SpringUtils.toFlatMap(request);
		context.put("ticket", ticket);
		
		String parameterStr = ExpressionFacotry.DOLOR.parse(SIGNATURE_TEMPLATE, context);
		String signature = Hashs.sha1().hash(parameterStr).toLowerCase();
		
		if (logger.isDebugEnabled()) {
			logger.debug("context: {}, signature: {}", context, signature);
		}
		
		JsApiSignatureResponse res = CopyUtils.copy(JsApiSignatureResponse.class, request);
		res.setSignature(signature);
		return res;
	}
	
	public void removeByAppid(String appid) {
		String key = getKey(appid);
		redisOperationService.clear(key);
	}
	
}

