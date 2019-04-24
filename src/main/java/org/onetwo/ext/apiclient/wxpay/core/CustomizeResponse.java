package org.onetwo.ext.apiclient.wxpay.core;

import java.util.Map;

/**
 * @author weishao zeng
 * <br/>
 */
public interface CustomizeResponse {
	
	void handle(Map<String, String> responseMap);

}

