package org.onetwo.ext.apiclient.qcloud.trtc;

import org.onetwo.ext.apiclient.qcloud.QCloudProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@ConfigurationProperties(TrtcProperties.PREFIX)
@Data
public class TrtcProperties {
	final public static String PREFIX = QCloudProperties.PREFIX + ".trtc";
//	final public static String ENABLED_KEY = PREFIX + ".enabled";
	
	Long sdkAppId;
	String secretKey;
	/**
	 * 签名过期时间，建议不要设置的过短
	 * <p>
	 * 时间单位：秒
	 * 默认为1天
	 */
	long defaultExpireTime = 86400L;
}
