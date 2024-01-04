package org.onetwo.ext.apiclient.qcloud.live.service;

import java.util.Date;

import lombok.Data;

import org.onetwo.ext.apiclient.qcloud.live.LiveProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.onetwo.common.utils.Assert;

/**
 * @author wayshall
 * <br/>
 */
public interface StreamDataProvider {
	
	StreamData create();

	@Data
	public class StreamData {
		String streamId;
		Date expiredAt;
		/****
		 * 是否录制
		 * record=mp4|hls|flv 分隔符格式用于指定同时录制一种以上的视频格式（只有 MP4 和 HLS 支持手机浏览器播放）；
		 * https://cloud.tencent.com/document/product/267/13373
		 */
		String record;
		String playDomain;
		String pushDomain;
		String pushSafeKey;
		String appname;
	}
	
	public class DefaultStreamDataProvider implements StreamDataProvider {
		@Autowired
		private LiveProperties liveConfig;

		@Override
		public StreamData create() {
			StreamData sdata = liveConfig.getStaticStream();
			Assert.hasText(sdata.getStreamId(), "streamId can not be null");
			Assert.notNull(sdata.getExpiredAt(), "expiredAt can not be null");
			return sdata;
		}
		
	}
}
