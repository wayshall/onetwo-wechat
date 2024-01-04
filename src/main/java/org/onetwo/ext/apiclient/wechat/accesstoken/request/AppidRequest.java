package org.onetwo.ext.apiclient.wechat.accesstoken.request;

import jakarta.validation.constraints.NotBlank;

import org.onetwo.common.annotation.IgnoreField;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenType;
import org.onetwo.ext.apiclient.wechat.accesstoken.spi.AccessTokenTypes;

import lombok.NoArgsConstructor;

/**
 * @author wayshall
 * <br/>
 */
//@Data
@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class AppidRequest {

	@NotBlank
	private String appid;
	/***
	 * 企业微信时使用
	 */
	private Long agentId;
	@IgnoreField
	private AccessTokenType accessTokenType;
	
//	@Builder
	public AppidRequest(String appid, Long agentId, AccessTokenType accessTokenType) {
		super();
		if (accessTokenType==null) {
			this.accessTokenType = AccessTokenTypes.WECHAT;
		} else {
			this.accessTokenType = accessTokenType;
		}
		this.appid = appid;
		this.agentId = agentId;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public AccessTokenType getAccessTokenType() {
		return accessTokenType;
	}

	public void setAccessTokenType(AccessTokenType accessTokenType) {
		this.accessTokenType = accessTokenType;
	}
	
	
}
