package org.onetwo.ext.apiclient.qcloud.live.api.message;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * （100）新录制文件
 * 
 * @author wayshall
 * 
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class RecordingMessage extends MessageHeader {
	/***
	 * 点播用 vid，在点播平台可以唯一定位一个点播视频文件
	 */
	@NotNull
	@JsonProperty("video_id")
	String videoId;
	/***
	 * 点播视频的下载地址
	 */
	@NotNull
	@JsonProperty("video_url")
	String videoUrl;
	
	/***
	 * 文件大小
	 */
	@JsonProperty("file_size")
	int fileSize;
	/***
	 * 分片开始时间戳
	 * 开始时间（unix 时间戳，由于 I 帧干扰，暂时不能精确到秒级）
	 */
	@JsonProperty("start_time")
	int startTime;
	/***
	 * 分片结束时间戳
	 * 结束时间（unix 时间戳，由于 I 帧干扰，暂时不能精确到秒级）
	 */
	@JsonProperty("end_time")
	int endTime;
	
	/***
	 */
	@JsonProperty("file_id")
	String fileId;
	/***
	 * 文件格式
	 * flv, hls, mp4
	 */
	@JsonProperty("file_format")
	String fileFormat;

	/***
	 * 是否开启点播 2.0
	 * 0 表示未开启，1 表示开启
	 */
	@JsonProperty("vod2Flag")
	int vod2Flag;
	
	/***
	 * 录制文件 ID
	 * 点播 2.0 开启时，才会有这个字段
	 */
	@JsonProperty("record_file_id")
	String recordFileId;
	
	@JsonProperty("stream_param")
	@NotNull
	String streamParam;
	/***
	 * 推流时长,单位 ms
	 */
	@JsonProperty("duration")
	String duration;

}
