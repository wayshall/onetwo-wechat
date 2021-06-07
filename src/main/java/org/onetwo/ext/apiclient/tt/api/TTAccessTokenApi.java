package org.onetwo.ext.apiclient.tt.api;

import org.onetwo.ext.apiclient.tt.core.TTApiClient;
import org.onetwo.ext.apiclient.tt.request.TTGetAccessTokenRequest;
import org.onetwo.ext.apiclient.wechat.basic.response.AccessTokenResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wayshall
 * <br/>
 */
@TTApiClient(path="/apps")
public interface TTAccessTokenApi {
	
	@RequestMapping(method=RequestMethod.GET, path="token")
	AccessTokenResponse getAccessToken(TTGetAccessTokenRequest request);
	
}
