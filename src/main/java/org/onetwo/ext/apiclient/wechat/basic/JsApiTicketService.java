package org.onetwo.ext.apiclient.wechat.basic;

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
import org.onetwo.ext.apiclient.wechat.basic.api.JsApiTicketApi;
import org.onetwo.ext.apiclient.wechat.basic.api.JsApiTicketApi.JsApiTicketResponse;
import org.onetwo.ext.apiclient.wechat.event.AccessTokenRefreshedEvent;
import org.onetwo.ext.apiclient.wechat.event.WechatEventListener;
import org.onetwo.ext.apiclient.work.basic.request.JsApiSignatureRequest;
import org.onetwo.ext.apiclient.work.basic.response.JsApiSignatureResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.onetwo.common.utils.Assert;

import com.google.common.eventbus.Subscribe;

/**
 * @author weishao zeng
 * <br/>
 */
@WechatEventListener
public class JsApiTicketService implements InitializingBean {
	private static final String SIGNATURE_TEMPLATE = "jsapi_ticket=${ticket}&noncestr=${noncestr}&timestamp=${timestamp}&url=${url}";
	static private final Logger logger = JFishLoggerFactory.getLogger(JsApiTicketService.class);
	
	@Autowired
	private JsApiTicketApi ticketApi;
	@Autowired
	private RedisOperationService redisOperationService;
	private String keyPrefix = "JsApiTicket:wechat";
	
	@Override
	public void afterPropertiesSet() throws Exception {
	}

	protected String getKey(String key) {
		return keyPrefix + key;
	}

	/****
	 * accesstoken 刷新时，移除js ticket
	 * @author weishao zeng
	 * @param event
	 */
	@Subscribe
	public void onAccessTokenRefreshed(AccessTokenRefreshedEvent event) {
		String configKey = getKey(event.getAppid());
		this.redisOperationService.clear(configKey);
	}
	
	/***
	 * 获取应用jsapi ticket
	 * @author weishao zeng
	 * @param accessToken
	 * @return
	 */
	public JsApiTicketResponse getJsApiTicket(AccessTokenInfo accessToken) {
		String key = getKey(accessToken.getAppid());
		return redisOperationService.getCache(key, () -> {
			JsApiTicketResponse res = ticketApi.getticket(accessToken, JsApiTicketApi.TYPE_CONFIG);
			return CacheData.<JsApiTicketResponse>builder().
												value(res)
												.expire(res.getExpiresIn())
												.timeUnit(TimeUnit.SECONDS)
												.build();
		});
	}
	
	/***
	 * 计算jsapi ticket 签名
	 * @author weishao zeng
	 * @param accessToken
	 * @param request
	 * @return
	 */
	public JsApiSignatureResponse getJsApiSignature(AccessTokenInfo accessToken, JsApiSignatureRequest request) {
		JsApiTicketResponse ticketRes = getJsApiTicket(accessToken);
		JsApiSignatureResponse sign = signature(ticketRes.getTicket(), request);
		sign.setAppid(accessToken.getAppid());
		return sign;
	}
	
	static public JsApiSignatureResponse signature(String ticket, JsApiSignatureRequest request) {
		Assert.notNull(request.getUrl(), "url can not be null");
//		Assert.notNull(request.getAppId(), "appid can not be null");
		if (request.getTimestamp()==null) {
			request.setTimestamp(DateUtils.now().getTime()/1000);
		}
		if (StringUtils.isBlank(request.getNoncestr())) {
			request.setNoncestr(RandomStringUtils.randomAlphanumeric(16));
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

