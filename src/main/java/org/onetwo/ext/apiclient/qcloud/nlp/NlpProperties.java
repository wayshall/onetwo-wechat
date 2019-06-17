package org.onetwo.ext.apiclient.qcloud.nlp;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@ConfigurationProperties(prefix=NlpProperties.PREFIX)
public class NlpProperties {
	public static final String PREFIX = "qcloud.nlp";

	public static final String ENABLE_KEY = PREFIX + ".enabled";

	private String secretId;
	private String secretKey;
	
}
