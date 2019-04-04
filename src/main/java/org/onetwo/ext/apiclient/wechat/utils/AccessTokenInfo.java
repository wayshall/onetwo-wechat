package org.onetwo.ext.apiclient.wechat.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.onetwo.common.date.DateUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	final public static int SHORTER_EXPIRE_TIME_IN_SECONDS = 60;
	final public static int UPDATE_NEWLY_DURATION_SECONDS = 10;

	private String appid;
	private String accessToken;
	private Date updateAt = null;
	private long expiresIn;

	public AccessTokenInfo(String accessToken) {
		this(null, accessToken, -1, null);
	}
	
	@Builder
	public AccessTokenInfo(String appid, String accessToken, long expiresIn, Date updateAt) {
		super();
		this.accessToken = accessToken;
//		this.expireAt = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn-SHORTER_EXPIRE_TIME_IN_SECONDS);
		//expiresIn 调用方已减
		this.expiresIn = expiresIn;
		this.updateAt = updateAt;
		this.appid = appid;
	}

	@JsonIgnore
	public Date getExpireAt() {
		Date expireAt = DateUtils.addSeconds(updateAt, Long.valueOf(expiresIn).intValue());
		return expireAt;
	}
	
	@JsonIgnore
	public boolean isExpired(){
		if(expiresIn == -1){
			//没有设置则不过期
			return false;
		}
		long current = System.currentTimeMillis();
		return current > getExpireAt().getTime();
	}
	
	/***
	 * 是否最近（五秒钟内）更新过
	 * @author weishao zeng
	 * @return
	 */
	@JsonIgnore
	public boolean isUpdatedNewly(){
		if(updateAt==null){
			return false;
		}
		long duration = System.currentTimeMillis() - updateAt.getTime();
//		long durationSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
//		System.out.println("===========>now: " + now+", update: " + updateAt.getTime()+", " + updateAt.toLocaleString() + ", duration: " + duration+", durationSeconds: " + durationSeconds);
		return  TimeUnit.MILLISECONDS.toSeconds(duration) < UPDATE_NEWLY_DURATION_SECONDS;
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
