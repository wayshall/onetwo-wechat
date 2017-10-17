package org.onetwo.ext.apiclient.wechat.poi.response;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wayshall
 * <br/>
 */
@Data
public class GetPoiListResponse {
	
	@JsonProperty("business_list")
	List<PoiBaseInfoWrapper> businessList;
	
	@Data
	public static class PoiBaseInfoWrapper {
		@JsonProperty("base_info")
		PoiBaseInfo baseInfo;
	}

	@Data
	public static class PoiBaseInfo {
		String sid;
		@JsonProperty("business_name")
		String businessName;
		@JsonProperty("branch_name")
		String branchName;//艺苑路店",
		String address;//":"艺苑路11号",
		String telephone;//":"020-12345678",
		Set<String> categories;//":["美食,快餐小吃"],
		String city;//":"广州市",
		String province;//":"广东省",
		@JsonProperty("offset_type")
		Integer offsetType;//":1,
		Double longitude;//":115.32375,
		Double latitude;//":25.097486,
//        "photo_list":[{"photo_url":"http: ...."}],
        String introduction;//":"麦当劳是全球大型跨国连锁餐厅，1940 年创立于美国，在世界上大约拥有3 万间分店。主要售卖汉堡包，以及薯条、炸鸡、汽水、冰品、沙拉、水果等快餐食品",
        String recommend;//":"麦辣鸡腿堡套餐，麦乐鸡，全家桶",
        String special;//":"免费wifi，外卖服务",
		@JsonProperty("open_time")
        LocalTime openTime;//":"8:00-20:00",
		@JsonProperty("avg_price")
        Double avgPrice;//":35,
        String poi_id;
		@JsonProperty("available_state")
        Integer availableState;//":3,
		String district;//":"海珠区",
		@JsonProperty("update_status")
		Integer updateStatus;//":0
	}

}
