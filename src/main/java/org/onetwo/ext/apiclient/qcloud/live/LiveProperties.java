package org.onetwo.ext.apiclient.qcloud.live;

import org.onetwo.ext.apiclient.qcloud.QCloudProperties;
import org.onetwo.ext.apiclient.qcloud.live.service.StreamDataProvider.StreamData;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@ConfigurationProperties(LiveProperties.PREFIX)
@Data
public class LiveProperties {
	/***
	 * wechat.qcloud.live
	 */
	final public static String PREFIX = QCloudProperties.PREFIX + ".live";
	final public static String ENABLE_KEY = PREFIX + ".enabled";
	final public static String CALLBACK_CONFIG = "${" + PREFIX + ".callback.path:callback}";
	final public static String CALLBACK_ENABLE_KEY = PREFIX + ".callback.enabled";
	
	String region = "ap-guangzhou";
	// 腾讯云新版直播已经废弃bizId
//	String bizId;
	String pushSafeKey;
	String appname = "live";

	String pushDomain;
//	String pushUrl = "rtmp://{bizId}.livepush.myqcloud.com/live/{streamId}?bizid={bizId}&txSecret={txSecret}&txTime={txTime}";
	String pushUrl = "rtmp://{pushDomain}/live/{streamId}?txSecret={txSecret}&txTime={txTime}";
	
	String playDomain = "liveplay.myqcloud.com";
//	String playUrl = "{protocol}://{bizId}.liveplay.myqcloud.com/live/{streamId}{postfix}";
	String playUrl = "{protocol}://{playDomain}/live/{streamId}{postfix}";
	/***
	 * https://cloud.tencent.com/document/product/267/13373
	 * 录制文件格式，如：record=mp4|flv
	 */
	String record;
	
	StreamData staticStream = new StreamData();
	
	/*public String getPlayDomain() {
		if (StringUtils.isBlank(playDomain)) {
			// {bizId}.liveplay.myqcloud.com
			return bizId + ".liveplay.myqcloud.com";
		}
		return playDomain;
	}
	
	public String getPushDomain() {
		if (StringUtils.isBlank(pushDomain)) {
			// {bizId}.livepush.myqcloud.com
			return bizId + ".livepush.myqcloud.com";
		}
		return pushDomain;
	}*/
}
