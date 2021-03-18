package org.onetwo.ext.apiclient.wechat.event;

import java.io.Serializable;

import lombok.Data;

/**
 * @author weishao zeng
 * <br/>
 */
@SuppressWarnings("serial")
@Data
public class AccessTokenRefreshedEvent implements Serializable {

	private String appid;
	private String accessToken;
	private Long agentId;
	
}
