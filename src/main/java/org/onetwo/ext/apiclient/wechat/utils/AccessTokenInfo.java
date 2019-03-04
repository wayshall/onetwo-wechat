package org.onetwo.ext.apiclient.wechat.utils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import lombok.Builder;
import lombok.Data;

/**
 * 必须有默认构造函数，json反序列化时需要
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Data
public class AccessTokenInfo implements Serializable {
	public static int SHORTER_EXPIRE_TIME_IN_SECONDS = 60;

	private String appid;
	private String accessToken;
	private long expireAt = -1;

	public AccessTokenInfo(String accessToken) {
		this(null, accessToken, -1);
	}
	
	@Builder
	public AccessTokenInfo(String appid, String accessToken, long expiresIn) {
		super();
		this.accessToken = accessToken;
//		this.expireAt = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn-SHORTER_EXPIRE_TIME_IN_SECONDS);
		//expiresIn 调用方已减
		if (expiresIn>=0) {
			this.expireAt = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn);
		} else {
			this.expireAt = -1;
		}
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


	public AccessTokenInfo() {
		super();
	}
}
