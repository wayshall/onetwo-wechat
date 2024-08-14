package org.onetwo.ext.apiclient.baidu.response;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LicensePlateResponse extends BaiduBaseResponse {

	@JsonProperty("words_result")
	WordsResult wordsResult;
	
	@Data
	public static class WordsResult {
		/***
		 * 车牌颜色：支持blue、green、yellow、white、black、yellow_green（新能源大型汽车黄绿车牌）、unknow（未知颜色）
		 */
		String color;
		/***
		 * 车牌号码
		 */
		String number;
		/***
		 * 前7个数字为车牌中每个字符的置信度，第8个数字为平均置信度，区间为0-1
		 */
		List<Double> probability;
		
		/****
		 * 返回文字外接多边形顶点位置
		 * x: 水平坐标（坐标0点为左上角）
		 * y: 垂直坐标（坐标0点为左上角）
		 */
		@JsonProperty("vertexes_location")
		List<Map<String, Integer>> vertexesLocation;
		
	}

}
