package org.onetwo.ext.apiclient.wechat.utils;

import java.util.concurrent.TimeUnit;

import lombok.Builder;
import lombok.Value;

/**
 * @author wayshall
 * <br/>
 */
@Value
@Builder
public class AccessTokenInfo {
	private String accessToken;
	private int expiresIn;
	private long updateAt = System.currentTimeMillis();
	
	public boolean isExpired(){
		long duration = System.currentTimeMillis() - updateAt;
		return TimeUnit.MILLISECONDS.toSeconds(duration) > expiresIn;
	}
	
}
