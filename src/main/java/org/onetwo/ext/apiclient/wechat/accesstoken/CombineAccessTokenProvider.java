package org.onetwo.ext.apiclient.wechat.accesstoken;

import java.util.List;
import java.util.stream.Collectors;

import org.onetwo.common.exception.ApiClientException;
import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.onetwo.ext.apiclient.wechat.utils.WechatClientErrors;

/**
 * @author weishao zeng
 * <br/>
 */
public class CombineAccessTokenProvider implements AccessTokenProvider {
	
//	@Autowired
	private List<AccessTokenProvider> accessTokenProviders;

	@Override
	public AccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
		AccessTokenProvider accessTokenProvider = accessTokenProviders.stream()
																	.filter(atp -> atp.getAccessTokenTypes().contains(request.getAccessTokenType()))
																	.findFirst()
																	.orElseThrow(() -> new ApiClientException(WechatClientErrors.ACCESS_TOKEN_SERVICE_NOT_FOUND));
		return accessTokenProvider.getAccessToken(request);
	}

	@Override
	public List<AccessTokenTypes> getAccessTokenTypes() {
		return accessTokenProviders.stream()
								.flatMap(atp -> atp.getAccessTokenTypes().stream())
								.collect(Collectors.toList());
	}

	public void setAccessTokenProviders(List<AccessTokenProvider> accessTokenProviders) {
		this.accessTokenProviders = accessTokenProviders;
	}
	
	
}
