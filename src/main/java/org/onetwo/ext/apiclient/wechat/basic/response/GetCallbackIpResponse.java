package org.onetwo.ext.apiclient.wechat.basic.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author wayshall
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class GetCallbackIpResponse extends WechatResponse {
	
	@JsonProperty("ip_list")
	private Set<String> ipList;

}
