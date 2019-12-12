package org.onetwo.ext.apiclient.qcloud;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author wayshall
 * <br/>
 */
@ConfigurationProperties(QCloudProperties.PREFIX)
@Data
public class QCloudProperties {

	final public static String PREFIX = "qcloud";
	
	//腾讯云账户secretId，secretKey
	String secretId;
	String secretKey;
}
