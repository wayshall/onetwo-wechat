package org.onetwo.ext.apiclient.qcloud.live.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.onetwo.common.expr.Expression;
import org.onetwo.common.expr.ExpressionFacotry;
import org.onetwo.common.utils.ParamUtils;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.qcloud.live.LiveProperties;
import org.onetwo.ext.apiclient.qcloud.live.service.StreamDataProvider;
import org.onetwo.ext.apiclient.qcloud.live.service.StreamDataProvider.StreamData;
import org.onetwo.ext.apiclient.qcloud.live.util.LiveUtils;
import org.onetwo.ext.apiclient.qcloud.live.util.LiveUtils.PlayTypes;
import org.onetwo.ext.apiclient.qcloud.live.vo.LivingResult;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

/**
 * @author wayshall
 * <br/>
 */
@Slf4j
public class QCloudLiveService {
	
	private LiveProperties liveProperties;
	private StreamDataProvider streamDataProvider;
	private Expression expr = ExpressionFacotry.BRACE;
	
	public LivingResult createLiving(){
		return createLiving(streamDataProvider);
	}
	
	public LivingResult createLiving(StreamDataProvider provider){
		Assert.notNull(provider, "provider can not be null");
		Assert.hasText(liveProperties.getBizId(), "bizId must not be null, empty, or blank");
		Assert.hasText(liveProperties.getPushSafeKey(), "pushSafeKey must not be null, empty, or blank");
		
		StreamData streamData = provider.create();
		String streamId = getStreamId(streamData.getStreamId());
		
		String pushUrl = createPushUrl(streamData);
		String urlTemplate = liveProperties.getPlayUrl();
		
		Map<String, Object> context = Maps.newHashMap();
		context.put("streamId", streamId);
		context.put("bizId", liveProperties.getBizId());
		
		LivingResult result = new LivingResult();
		result.setPushUrl(pushUrl);
		result.setPlayRtmp(PlayTypes.RTMP.getPlayUrl(expr, urlTemplate, context));
		result.setPlayFlv(PlayTypes.FLV.getPlayUrl(expr, urlTemplate, context));
		result.setPlayHls(PlayTypes.HLS.getPlayUrl(expr, urlTemplate, context));
		return result;
	}
	
	protected String getStreamId(final String streamId){
		if(!streamId.startsWith(liveProperties.getBizId()+"_")){
			return liveProperties.getBizId()+"_" + streamId;
		}
		return streamId;
	}
	protected String createPushUrl(StreamData streamData){
		String streamId = getStreamId(streamData.getStreamId());
		String urlTemplate = liveProperties.getPushUrl();
		Map<String, Object> createPushContext = Maps.newHashMap();
		String txTime = createTxTime(streamData.getExpiredAt());
		String txSecret = createTxSecret(liveProperties.getPushSafeKey(), streamId, txTime).toLowerCase();
		
		
		createPushContext.put("bizId", liveProperties.getBizId());
		createPushContext.put("streamId", streamId);
		createPushContext.put("txTime", txTime);
		createPushContext.put("txSecret", txSecret);
		
		String pushUrl = expr.parseByProvider(urlTemplate, createPushContext);
		String record = StringUtils.firstNotBlank(streamData.getRecord(), liveProperties.getRecord());
		if(StringUtils.isNotBlank(record)){
			pushUrl = ParamUtils.appendParam(pushUrl, "record", record);
		}
		if(log.isInfoEnabled()){
			log.info("pushUrl: {}, createPushContext: {}", pushUrl, createPushContext);
		}
		return pushUrl;
	}
	
	protected String createTxTime(Date expiredAt){
		long txTime = TimeUnit.MILLISECONDS.toSeconds(expiredAt.getTime());
		return Long.toHexString(txTime).toUpperCase();
	}
	
	protected String createTxSecret(String key, String streamId, String txTime){
		return LiveUtils.buildTxSecret(key, streamId, txTime);
	}

	public void setStreamDataProvider(StreamDataProvider streamDataProvider) {
		this.streamDataProvider = streamDataProvider;
	}

	public void setLiveProperties(LiveProperties liveProperties) {
		this.liveProperties = liveProperties;
	}
}
