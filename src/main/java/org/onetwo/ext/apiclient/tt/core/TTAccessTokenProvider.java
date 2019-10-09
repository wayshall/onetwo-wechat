package org.onetwo.ext.apiclient.tt.core;

import java.util.Arrays;
import java.util.List;

import org.onetwo.ext.apiclient.wechat.accesstoken.request.GetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenProvider;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenType;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;

/**
 * @author weishao zeng
 * <br/>
 */
public class TTAccessTokenProvider implements AccessTokenProvider {

	@Override
	public AccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AccessTokenType> getAccessTokenTypes() {
		return Arrays.asList(TTAccessTokenTypes.TTAPP);
	}
	
	static public enum TTAccessTokenTypes implements AccessTokenType {
		TTAPP
	}
}

