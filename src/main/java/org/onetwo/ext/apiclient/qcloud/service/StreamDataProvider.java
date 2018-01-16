package org.onetwo.ext.apiclient.qcloud.service;

import java.util.Date;

import lombok.Data;

import org.onetwo.ext.apiclient.qcloud.LiveProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

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
		String record;
	}
	
	public class DefaultStreamDataProvider implements StreamDataProvider {
		@Autowired
		private LiveProperties liveConfig;

		@Override
		public StreamData create() {
			StreamData sdata = liveConfig.getStaticStream();
			Assert.hasText(sdata.getStreamId());
			Assert.notNull(sdata.getExpiredAt());
			return sdata;
		}
		
	}
}
