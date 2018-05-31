package org.onetwo.ext.apiclient.wechat.utils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import lombok.Builder;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
public class AccessTokenInfo implements Serializable {
	public static int SHORTER_EXPIRE_TIME_IN_SECONDS = 60;

	final private String appid;
	final private String accessToken;
	
	private long expireAt = -1;

	public AccessTokenInfo(String accessToken) {
		this(null, accessToken, -1);
	}
	

	@Builder
	public AccessTokenInfo(String appid, String accessToken, int expiresIn) {
		super();
		this.accessToken = accessToken;
//		this.expireAt = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn-SHORTER_EXPIRE_TIME_IN_SECONDS);
		//expiresIn 调用方已减
		this.expireAt = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn);
		this.appid = appid;
	}
	
	public boolean isExpired(){
		if(expireAt==-1){
			//没有设置则不过期
			return false;
		}
		long current = System.currentTimeMillis();
		return current > expireAt;
	}


	public String getAppid() {
		return appid;
	}


	public String getAccessToken() {
		return accessToken;
	}
}
