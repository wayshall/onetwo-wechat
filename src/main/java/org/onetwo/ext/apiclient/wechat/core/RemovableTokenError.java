package org.onetwo.ext.apiclient.wechat.core;

import org.onetwo.common.exception.ApiClientException;

/**
 * @author weishao zeng
 * <br/>
 */

public interface RemovableTokenError {
	
	boolean isNeedToRemoveToken(ApiClientException e);

}
