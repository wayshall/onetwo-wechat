package org.onetwo.ext.apiclient.qcloud.nlp;

import org.onetwo.ext.apiclient.qcloud.QCloudProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@ConfigurationProperties(prefix=NlpProperties.PREFIX)
public class NlpProperties {
	public static final String PREFIX = QCloudProperties.PREFIX + ".nlp";

	public static final String ENABLE_KEY = PREFIX + ".enabled";

	@Deprecated
	private String secretId;
	@Deprecated
	private String secretKey;
	
	private String region = "ap-guangzhou";
	private String version = "2019-04-08";
	
}
