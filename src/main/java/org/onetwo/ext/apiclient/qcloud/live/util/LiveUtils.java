package org.onetwo.ext.apiclient.qcloud.live.util;

import java.util.Map;

import org.onetwo.common.expr.Expression;
import org.onetwo.common.md.Hashs;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.ext.apiclient.qcloud.exception.QCloudException;

/**
 * @author wayshall
 * <br/>
 */
abstract public class LiveUtils {
	/***
	 * txSecret
	 * @author wayshall
	 * @param key
	 * @param streamId
	 * @param txTime
	 * @return
	 */
	static public String buildTxSecret(String pushSafeKey, String streamId, String txTime) {
		if (StringUtils.isBlank(pushSafeKey)) {
			throw new QCloudException("pushSafeKey can not be blank");
		}
		if (StringUtils.isBlank(streamId)) {
			throw new QCloudException("streamId can not be blank");
		}
		if (StringUtils.isBlank(txTime)) {
			throw new QCloudException("txTime can not be blank");
		}
		String input = new StringBuilder().append(pushSafeKey)
											.append(streamId)
											.append(txTime.toUpperCase())
											.toString();
		String secret = Hashs.MD5.hash(input);
		return secret;
	}
	
	public static enum PlayTypes {
		RTMP("rtmp", ""),
		FLV("http", ".flv"),
		HLS("http", ".m3u8");
		private final String protocol;
		private final String postfix;
		private PlayTypes(String protocol, String postfix) {
			this.protocol = protocol;
			this.postfix = postfix;
		}
		public String getProtocol() {
			return protocol;
		}
		public String getPostfix() {
			return postfix;
		}
		public String getPlayUrl(Expression expr, String palyUrlTemplate, Map<String, Object> context){
			context.put("protocol", protocol);
			context.put("postfix", postfix);
			return expr.parseByProvider(palyUrlTemplate, context);
		}

	}
	
	public static class EventTypes {
		
		public static final int CLOSE = 0;
		public static final int PUSH = 1;
		public static final int RECORDING = 100;
		public static final int SCREEN_SHOT = 200;
		
		private EventTypes(){
		}
		
	}
	
	private LiveUtils(){
	}
}
