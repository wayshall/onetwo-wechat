package org.onetwo.ext.apiclient.qcloud;

import lombok.Data;

import org.onetwo.ext.apiclient.qcloud.service.StreamDataProvider.StreamData;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wayshall
 * <br/>
 */
@ConfigurationProperties("wechat.qcloud.live")
@Data
public class LiveProperties {

	final public static String ENABLE_KEY = "wechat.qcloud.live.enabled";
	final public static String CALLBACK_CONFIG = "${wechat.qcloud.live.callback.path:callback}";
	final public static String CALLBACK_ENABLE_KEY = "wechat.qcloud.live.callback.enabled";
	
	String bizId;
	String pushSafeKey;
	String pushUrl = "rtmp://{bizId}.livepush.myqcloud.com/live/{streamId}?bizid={bizId}&txSecret={txSecret}&txTime={txTime}";
	String playUrl = "{protocol}://{bizId}.liveplay.myqcloud.com/live/{streamId}{postfix}";
	/***
	 * https://cloud.tencent.com/document/product/267/13373
	 * 录制文件格式，如：record=mp4|flv
	 */
	String record;
	
	StreamData staticStream = new StreamData();
	
	
}
