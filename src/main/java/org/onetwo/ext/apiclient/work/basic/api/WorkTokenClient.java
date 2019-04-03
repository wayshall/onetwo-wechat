package org.onetwo.ext.apiclient.work.basic.api;

import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.work.core.WorkWechatApiClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.Builder;
import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@WorkWechatApiClient
public interface WorkTokenClient {

	@RequestMapping(method=RequestMethod.GET, path="gettoken")
	AccessTokenResponse getAccessToken(GetTokenRequest request);
	
	@Data
	@Builder
	public class GetTokenRequest {
		private String corpid;
		private String corpsecret;
	}
	
}

