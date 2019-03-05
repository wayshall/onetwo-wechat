package org.onetwo.ext.apiclient.wechat.utils;

import java.io.Serializable;
import java.util.Date;

import org.onetwo.common.date.DateUtils;

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
	private Date expireAt = null;
	private long expiresIn;

	public AccessTokenInfo(String accessToken) {
		this(null, accessToken, -1, null);
	}
	
	@Builder
	public AccessTokenInfo(String appid, String accessToken, long expiresIn, Date createAt) {
		super();
		this.accessToken = accessToken;
//		this.expireAt = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn-SHORTER_EXPIRE_TIME_IN_SECONDS);
		//expiresIn 调用方已减
		this.expiresIn = expiresIn;
		if (createAt!=null && expiresIn>=0) {
//			this.expireAt = createAt.getTime() + TimeUnit.SECONDS.toMillis(expiresIn);
			this.expireAt = DateUtils.addSeconds(createAt, Long.valueOf(expiresIn).intValue());
		} else {
			this.expireAt = null;
		}
		this.appid = appid;
	}
	
	public boolean isExpired(){
		if(expireAt==null){
			//没有设置则不过期
			return false;
		}
		long current = System.currentTimeMillis();
		return current > expireAt.getTime();
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
