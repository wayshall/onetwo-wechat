package org.onetwo.ext.apiclient.qcloud.live.endpoint;

import java.util.Map;

/**
 * @author wayshall
 * <br/>
 */
public interface CallbackEndpoint {

	Object callback(Map<String, Object> messageBody);

}