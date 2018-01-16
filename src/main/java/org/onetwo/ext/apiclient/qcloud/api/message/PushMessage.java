package org.onetwo.ext.apiclient.qcloud.api.message;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.onetwo.ext.apiclient.qcloud.util.LiveUtils.EventTypes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * (1)推流 (0)断流
 * 
 * @author wayshall
 * 
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class PushMessage extends MessageHeader {
	/***
	 * 推流路径
	 */
	@NotNull
	String appname;
	/***
	 * 推流域名
	 */
	@NotNull
	String app;
	
	/***
	 * 消息产生的时间,单位 s
	 */
	@JsonProperty("event_time")
	int eventTime;
	
	/***
	 * 消息序列号，标识一次推流活动，一次推流活动会产生相同序列号的推流和断流消息
	 */
	@JsonProperty("sequence")
	String sequence;
	/***
	 * Upload 接入点的 IP
	 */
	String node;

	@JsonProperty("user_ip")
	String userIp;
	
	/***
	 * 断流错误码
	 */
	int errcode;
	/***
	 * 断流错误信息
	 */
	String errmsg;
	
	/****
	 * 推流url参数
	 */
	@JsonProperty("stream_param")
	@NotNull
	String streamParam;
	/***
	 * 推流时长,单位 ms
	 */
	@JsonProperty("push_duration")
	String pushDuration;
	
	public boolean isPushEvent(){
		return eventType==EventTypes.PUSH;
	}
	
	public boolean isCloseEvent(){
		return eventType==EventTypes.CLOSE;
	}

}
