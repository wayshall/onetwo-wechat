package org.onetwo.ext.apiclient.wechat.basic.response;

import java.util.Set;

import lombok.Data;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wayshall
 * <br/>
 */
@Data
@ToString
public class GetCallbackIpResponse {
	
	@JsonProperty("ip_list")
	private Set<String> ipList;

}
