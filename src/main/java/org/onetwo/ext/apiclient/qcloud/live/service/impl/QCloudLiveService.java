package org.onetwo.ext.apiclient.qcloud.live.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.onetwo.common.date.NiceDate;
import org.onetwo.common.exception.BaseException;
import org.onetwo.common.expr.Expression;
import org.onetwo.common.expr.ExpressionFacotry;
import org.onetwo.common.utils.ParamUtils;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.qcloud.exception.QCloudException;
import org.onetwo.ext.apiclient.qcloud.live.LiveProperties;
import org.onetwo.ext.apiclient.qcloud.live.service.StreamDataProvider;
import org.onetwo.ext.apiclient.qcloud.live.service.StreamDataProvider.StreamData;
import org.onetwo.ext.apiclient.qcloud.live.util.LiveErrors.LiveBizErrors;
import org.onetwo.ext.apiclient.qcloud.live.util.LiveStreamStates;
import org.onetwo.ext.apiclient.qcloud.live.util.LiveUtils;
import org.onetwo.ext.apiclient.qcloud.live.util.LiveUtils.PlayTypes;
import org.onetwo.ext.apiclient.qcloud.live.vo.LivingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wayshall
 * <br/>
 */
@Slf4j
public class QCloudLiveService {
	
	private LiveProperties liveProperties;
	private StreamDataProvider streamDataProvider;
	private Expression expr = ExpressionFacotry.BRACE;
	
	@Autowired(required=false)
	private QCloudLiveClient qcloudLiveClient;
	

	public LiveStreamStates getLiveStatus(String streamName) {
		return qcloudLiveClient.getLiveStatus(streamName);
	}
	
	public LivingResult createLiving(){
		return createLiving(streamDataProvider);
	}
	
	public LivingResult createLiving(StreamDataProvider provider){
		Assert.notNull(provider, "provider can not be null");
//		Assert.hasText(liveProperties.getBizId(), "bizId must not be null, empty, or blank");
//		Assert.hasText(liveProperties.getPushSafeKey(), "pushSafeKey must not be null, empty, or blank");
		
		StreamData streamData = provider.create();
		String streamId = getStreamId(streamData.getStreamId());
		
		// 创建推流地址
		String pushUrl = createPushUrl(streamData);
		String urlTemplate = liveProperties.getPlayUrl();
		
		Map<String, Object> context = Maps.newHashMap();
		context.put("streamId", streamId);
//		context.put("bizId", liveProperties.getBizId());
		String playDomain = streamData.getPlayDomain();
		if (StringUtils.isBlank(playDomain)) {
			playDomain = liveProperties.getPlayDomain();
		}
		if (StringUtils.isBlank(playDomain)) {
			throw new BaseException("playDomain can not be null or blank");
		}
		context.put("playDomain", playDomain);
		
		LivingResult result = new LivingResult();
		result.setPushUrl(pushUrl);
		result.setPlayRtmp(PlayTypes.RTMP.getPlayUrl(expr, urlTemplate, context));
		result.setPlayFlv(PlayTypes.FLV.getPlayUrl(expr, urlTemplate, context));
		result.setPlayHls(PlayTypes.HLS.getPlayUrl(expr, urlTemplate, context));
		return result;
	}
	
	protected String getStreamId(final String streamId){
		/*if(!streamId.startsWith(liveProperties.getBizId()+"_")){
			return liveProperties.getBizId()+"_" + streamId;
		}*/
		return streamId;
	}
	protected String createPushUrl(StreamData streamData){
		String streamId = getStreamId(streamData.getStreamId());
		String urlTemplate = liveProperties.getPushUrl();
		Map<String, Object> createPushContext = Maps.newHashMap();
		
		Date expiredAt = streamData.getExpiredAt();
		if (expiredAt==null) {
    		// 精确到当天的最后时间，去掉毫秒
			expiredAt = NiceDate.New()
									.preciseAtDate()
									.atTheEnd()
									.clearMillis()
									.getTime();
		} else {
			expiredAt = NiceDate.New(expiredAt)
									.clearMillis()
									.getTime();
		}
		if (expiredAt.getTime()<=new Date().getTime()) {
			throw new QCloudException(LiveBizErrors.ERR_LIVE_STREAM_EXPIRED);
		}
		
		String txTime = createTxTime(expiredAt);
		String pushSafeKey = streamData.getPushSafeKey();
		if (StringUtils.isBlank(pushSafeKey)) {
			pushSafeKey = liveProperties.getPushSafeKey();
		}
		String txSecret = createTxSecret(pushSafeKey, streamId, txTime).toLowerCase();
		
		
//		createPushContext.put("bizId", liveProperties.getBizId());
		createPushContext.put("streamId", streamId);
		createPushContext.put("txTime", txTime);
		createPushContext.put("txSecret", txSecret);
		
		String pushDomain = streamData.getPushDomain();
		if (StringUtils.isBlank(pushDomain)) {
			pushDomain = liveProperties.getPushDomain();
		}
		if (StringUtils.isBlank(pushDomain)) {
			throw new BaseException("pushDomain can not be null or blank");
		}
		createPushContext.put("pushDomain", pushDomain);
		
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
