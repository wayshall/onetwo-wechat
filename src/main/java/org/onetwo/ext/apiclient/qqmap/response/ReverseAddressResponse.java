package org.onetwo.ext.apiclient.qqmap.response;

import org.onetwo.ext.apiclient.qqmap.response.ReverseLocationResponse.AddressComponent;
import org.onetwo.ext.apiclient.qqmap.response.ReverseLocationResponse.LocationInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class ReverseAddressResponse extends BaseResponse {

	private ReverseResult result;

	@Data
	public static class ReverseResult {
		private String title;
		
		private String address;
		@JsonProperty("address_component")
		private AddressComponent addressComponent;
		
		@JsonProperty("location")
		private LocationInfo location;
		
		/***
		 * 解析精度级别，分为11个级别，一般>=9即可采用（定位到点，精度较高） 也可根据实际业务需求自行调整，完整取值表见下文。
		 */
		int level;
		
	}

}

