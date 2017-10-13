package org.onetwo.ext.apiclient.wechat.utils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import lombok.Builder;
import lombok.Value;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Value
public class AccessTokenInfo implements Serializable {
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
