package org.onetwo.ext.apiclient.qqmap.response;

import java.util.List;

import javax.validation.constraints.NotNull;

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
public class DistrictResponse extends BaseResponse {

	/***
	 * 行政区划数据版本，用于定期更新
	 */
	@JsonProperty("data_version")
	String dataVersion;
	
	private List<List<RegionResult>> result;
	
	@Data
	public static class RegionResult {
		/***
		 * 行政区划唯一标识
注：省直辖地区，在数据表现上有一个重复的虚拟节点（其id最后两位为99），其目的是为了表明省直辖关系而增加的，开发者可根据实际需要选用
		 */
		@NotNull
		String id;
		/***
		 * 简称，如“内蒙古”
		 */
		String name;
		@NotNull
		/***
		 * 全称，如“内蒙古自治区”
		 */
		String fullname;
		/***
		 * 中心点坐标
		 */
		@NotNull
		LocationInfo location;
		/***
		 * 行政区划拼音，每一下标为一个字的全拼，如：["nei","meng","gu"]
		 */
		List<String> pinyin;
		/***
		 * 子级行政区划在下级数组中的下标位置
		 */
		List<Integer> cidx;
	}

}

