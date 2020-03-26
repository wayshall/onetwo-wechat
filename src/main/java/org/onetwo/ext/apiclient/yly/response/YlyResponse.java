package org.onetwo.ext.apiclient.yly.response;

import org.onetwo.ext.apiclient.wechat.basic.response.WechatResponsable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wayshall
 * <br/>
 */
@Data
@Builder(builderMethodName="baseBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class YlyResponse implements WechatResponsable {
	
	private String error;
	@JsonProperty("error_description")
	private String errorDescription;
	
	public boolean isSuccess(){
		return "0".equals(error);
	}

}
