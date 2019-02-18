package org.onetwo.ext.apiclient.qcloud.live.endpoint.impl;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.onetwo.ext.apiclient.qcloud.live.LiveProperties;
import org.onetwo.ext.apiclient.qcloud.live.endpoint.CallbackEndpoint;
import org.onetwo.ext.apiclient.qcloud.live.service.LiveMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

/**
 * @author wayshall
 * <br/>
 */
@RequestMapping(LiveProperties.CALLBACK_CONFIG)
@RestController
@Slf4j
public class CallbackController implements CallbackEndpoint {
	
	@Autowired
	private LiveMessagePublisher liveMessagePublisher;
	
	@Override
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Object callback(@RequestBody Map<String, Object> messageBody){
		log.info("messageBody: {}", messageBody);
		liveMessagePublisher.publish(messageBody);
		
		// 在收到消息通知的http请求里返回错误码 0 以代表您已经成功收到了消息，从而避免腾讯云反复重复通知
		return ImmutableMap.of("code", 0);
	}

}
