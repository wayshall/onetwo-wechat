package org.onetwo.ext.apiclient.wechat.utils;

import java.util.concurrent.TimeUnit;

import lombok.Builder;
import lombok.Value;

/**
 * @author wayshall
 * <br/>
 */
@Value
public class AccessTokenInfo {
	private String accessToken;
	private int expiresIn;
	private long updateAt = System.currentTimeMillis();

	@Builder
	public AccessTokenInfo(String accessToken, int expiresIn) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
	}
	
	public boolean isExpired(){
		long duration = System.currentTimeMillis() - updateAt;
		return TimeUnit.MILLISECONDS.toSeconds(duration) > expiresIn;
	}
	
}
