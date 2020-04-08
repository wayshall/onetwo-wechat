package org.onetwo.ext.apiclient.wechat.core;
/**
 * @author weishao zeng
 * <br/>
 */

public interface RemovableTokenError {
	
	boolean isNeedToRemoveToken(String errorCode);

}
