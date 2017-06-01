package org.onetwo4j.sample.service;

import org.onetwo.common.spring.rest.JFishRestTemplate;
import org.onetwo.common.spring.validator.ValidatorWrapper;
import org.onetwo4j.sample.WechatConfig;
import org.onetwo4j.sample.request.AccessTokenRequest;
import org.onetwo4j.sample.response.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wayshall
 * <br/>
 */
@Service
public class BaseSupportServiceImpl {
	
	@Autowired
	private WechatConfig wechatConfig;
	@Autowired
	private ValidatorWrapper validatorWrapper;
	@Autowired
	private JFishRestTemplate restTemplate;
	
	public String getAccessToken(){
		AccessTokenRequest request = AccessTokenRequest.builder()
														.grantType(wechatConfig.getGrantType())
														.appid(wechatConfig.getAppid())
														.secret(wechatConfig.getAppsecret())
														.build();
		validatorWrapper.throwIfValidateFailed(request);
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		this.restTemplate.get(url, request, AccessTokenResponse.class);
	}

}
