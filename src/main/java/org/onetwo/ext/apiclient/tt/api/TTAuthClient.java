package org.onetwo.ext.apiclient.tt.api;

import org.onetwo.ext.apiclient.tt.core.TTApiClient;
import org.onetwo.ext.apiclient.tt.request.TTAuthRequest;
import org.onetwo.ext.apiclient.tt.response.TTAuthResponse;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * https://developer.toutiao.com/dev/miniapp/uUjNz4SN2MjL1YzM
 * 
 * @author wayshall
 * <br/>
 */
@TTApiClient
public interface TTAuthClient {

	/***
	 * @author wayshall
	 * @param request
	 * @return
	 */
	@GetMapping(value="/apps/jscode2session")
	TTAuthResponse jscode2session(TTAuthRequest request);

}
