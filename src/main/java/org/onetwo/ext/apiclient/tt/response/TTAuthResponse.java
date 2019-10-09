package org.onetwo.ext.apiclient.tt.response;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class TTAuthResponse extends WechatResponse {
	
	String openid;
	@JsonProperty("session_key")
	String sessionKey;
	@JsonProperty("anonymous_openid")
	String anonymousOpenid;

}
