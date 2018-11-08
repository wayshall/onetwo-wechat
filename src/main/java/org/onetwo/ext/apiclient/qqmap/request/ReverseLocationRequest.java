package org.onetwo.ext.apiclient.qqmap.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weishao zeng
 * <br/>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReverseLocationRequest {
	
	private String key;
	/***
	 * 位置坐标，格式：
location=lat<纬度>,lng<经度>
	 */
	private String location;
	
	/***
	 * 是否返回周边POI列表：
1.返回；0不返回(默认)
	 */
	@JsonProperty("get_poi")
	private int getPoi;

}

