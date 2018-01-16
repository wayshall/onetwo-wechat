package org.onetwo.ext.apiclient.qcloud.api.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * https://cloud.tencent.com/document/api/267/5957
 * @author wayshall
 * <br/>
 */
@Data
abstract public class MessageHeader {
	
	/***
	 * UNIX 时间戳（十进制）
	 */
	String t;
	/***
	 * MD5(KEY+t)
	 */
	String sign;
	/***
	 * 目前可能值为： 0、1、100、200
	 */
	@JsonProperty("event_type")
	int eventType;
	/***
	 * 标示事件源于哪一条直播流
	 */
	@JsonProperty("stream_id")
	String streamId;
	/***
	 * 同 stream_id
	 */
	@JsonProperty("channel_id")
	String channelId;

}
