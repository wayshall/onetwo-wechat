package org.onetwo.ext.apiclient.qcloud.live.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@SuppressWarnings("serial")
@Data
public class LivingResult implements Serializable {
	
	String pushUrl;
	String playRtmp;
	String playFlv;
	String playHls;
	
	/***
	 * 低延时播放地址（播放会有更低的时延，主要用于实时音视频和连麦场景）
	 */
//	String accelerateUrl;

}
