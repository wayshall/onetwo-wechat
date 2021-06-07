package org.onetwo.ext.apiclient.wechat.accesstoken.response;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.onetwo.common.date.DateUtils;
import org.onetwo.common.date.NiceDate;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenType;

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
	private Date expireAt;
	/***
	 * 企业微信有用
	 */
	private Long agentId;
	
//	private boolean autoAppendToUrl = true;
	private AccessTokenType accessTokenType;

	/***
	 * 必须有默认构造函数，json反序列化时需要
	 */
	public AccessTokenInfo() {
	}
	
	@Builder
	public AccessTokenInfo(String appid, Long agentId, String accessToken, long expiresIn, Date updateAt, Date expireAt) {
		super();
		this.accessToken = accessToken;
//		this.expireAt = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(expiresIn-SHORTER_EXPIRE_TIME_IN_SECONDS);
		//expiresIn 调用方已减
		this.expiresIn = expiresIn;
		this.updateAt = updateAt;
		if (expireAt!=null) {
			this.expireAt = expireAt;
		}else if (updateAt!=null) {
			this.expireAt = DateUtils.addSeconds(updateAt, (int)expiresIn);
		}
		this.appid = appid;
		this.agentId = agentId;
	}

	@JsonIgnore
	public Date getExpireAt() {
//		if (updateAt==null) {
//			return null;
//		}
//		Date expireAt = DateUtils.addSeconds(updateAt, Long.valueOf(expiresIn).intValue());
		return expireAt;
	}
	
	@JsonIgnore
	public boolean isExpired(){
		Date expireAt = getExpireAt();
		if (expireAt==null) {
			if(updateAt!=null && expiresIn > 0){
				expireAt = NiceDate.New(updateAt).nextSecond((int)expiresIn).getTime();
			} else {
				// 如果时间相关的字段为null，则视为不会过期
				return false;
			}
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
	
	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
}
