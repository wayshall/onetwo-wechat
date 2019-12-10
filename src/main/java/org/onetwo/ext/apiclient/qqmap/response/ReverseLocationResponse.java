package org.onetwo.ext.apiclient.qqmap.response;

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
public class ReverseLocationResponse extends BaseResponse {

	private ReverseResult result;

	@Data
	public static class ReverseResult {
		private String address;
		@JsonProperty("address_component")
		private AddressComponent addressComponent;
		
		// address_reference
		@JsonProperty("address_reference")
		private AddressRef addressReference;
		
	}
	
	@Data
	public static class AddressComponent {
		private String nation;
		private String province;
		private String city;
		private String district;
		private String street;
		@JsonProperty("street_number")
		private String streetNumber;
	}

	@Data
	public static class AddressRef {
		private AddressRefPart town;
		private AddressRefPart street;
		private AddressRefPart crossroad;
		private AddressRefPart landmark_l2;
	}

	@Data
	public static class AddressRefPart {
		private String id;
		private String title;
		private LocationInfo location;
		@JsonProperty("_distance")
		private float distance;
		@JsonProperty("_dir_desc")
		private String dirDesc;
	}

	@Data
	public static class LocationInfo {
		private float lat;
		private float lng;
	}
}

