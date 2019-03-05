package org.onetwo.ext.apiclient.wechat.core;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.onetwo.common.apiclient.ApiClientMethod;
import org.onetwo.common.apiclient.ApiErrorHandler;
import org.onetwo.common.apiclient.utils.ApiClientConstants.ApiClientErrors;
import org.onetwo.common.exception.ApiClientException;
import org.onetwo.ext.apiclient.wechat.utils.AccessTokenInfo;
import org.onetwo.ext.apiclient.wechat.utils.WechatErrors;

/**
 * @author weishao zeng
 * <br/>
 */
public class WechatApiErrorHandler implements ApiErrorHandler {

	@Override
	public Object handleError(ApiClientMethod invokeMethod, Exception ex) {
		if (ex instanceof ApiClientException) {
			ApiClientException e = (ApiClientException) ex;
			if(WechatErrors.isNeedToRemoveToken(e.getCode()) && 
					invokeMethod.getAccessTokenParameter().isPresent()){
				Optional<AccessTokenInfo> at = invokeMethod.getAccessToken(invocation.getArguments());
				if(at.isPresent() && StringUtils.isNotBlank(at.get().getAppid())){
					if (autoRemove) {
						String appid = at.get().getAppid();
						logger.info("accesstoken is invalid, try to remove  ...");
						getAccessTokenService().removeAccessToken(appid);
					} else {
						logger.warn("accesstoken is invalid and disable auto remove");
					}
				} else {
					logger.warn("accesstoken is invalid and AccessTokenInfo not found");
				}
			}
		} else {
			throw new ApiClientException(ApiClientErrors.EXECUTE_REST_ERROR, invokeMethod.getMethod(), e);
		}
	}
	
	

}

